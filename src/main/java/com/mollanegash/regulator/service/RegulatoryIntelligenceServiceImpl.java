package com.mollanegash.regulator.service;

import com.mollanegash.regulator.model.RegulatoryInsight;
import com.mollanegash.regulator.repository.RegulatoryInsightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RegulatoryIntelligenceServiceImpl implements RegulatoryIntelligenceService {

    private static final Logger log = LoggerFactory.getLogger(RegulatoryIntelligenceServiceImpl.class);

    private final RegulatoryInsightRepository vectorRepository;
    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;

    public RegulatoryIntelligenceServiceImpl(RegulatoryInsightRepository vectorRepository,
                                              ChatClient.Builder builder,
                                              EmbeddingModel embeddingModel) {
        this.vectorRepository = vectorRepository;
        this.chatClient = builder
                .defaultSystem("You are an expert in federal regulations.")
                .build();
        this.embeddingModel = embeddingModel;
    }

    @Override
    @Transactional
    public String getRegulatoryInsight(String query) {
        log.info("Starting regulatory insight generation for query: {}", query);

        if (query == null || query.isBlank()) {
            return "Query must not be empty.";
        }

        List<Float> queryEmbedding = prepareQueryEmbedding(query);
        Optional<RegulatoryInsight> similarityMatch = vectorRepository.findTopByEmbeddingSimilarity(queryEmbedding);

        String context = similarityMatch.map(this::formatContext)
                .orElse("No relevant regulatory context was found for the query.");

        String densityResponse = chatClient.prompt()
                .system(s -> s.text("You are a regulatory analysis assistant. Use the provided context to determine the regulatory requirement density for the query. " +
                        "Respond with numeric density only, as a single number representing percentage. Do not include text, units, or explanation."))
                .user(u -> u.text("Context:\n{context}\n\nUser query:\n{query}")
                        .param("context", context)
                        .param("query", query))
                .call()
                .content();

        double requirementDensity = parseDensity(densityResponse);
        RegulatoryInsight insightToSave = buildInsightForPersistence(similarityMatch, context, requirementDensity);
        vectorRepository.save(insightToSave);

        return String.format("%.2f", requirementDensity);
    }

    private RegulatoryInsight buildInsightForPersistence(Optional<RegulatoryInsight> similarityMatch,
                                                         String context,
                                                         double requirementDensity) {
        RegulatoryInsight insight = similarityMatch.orElseGet(RegulatoryInsight::new);
        if (insight.getAgencyName() == null || insight.getAgencyName().isBlank()) {
            insight.setAgencyName("Unknown");
        }
        if (insight.getSummary() == null || insight.getSummary().isBlank()) {
            insight.setSummary(context);
        }
        if (insight.getComplianceRiskLevel() == null || insight.getComplianceRiskLevel().isBlank()) {
            insight.setComplianceRiskLevel("UNKNOWN");
        }
        if (insight.getEmbeddingVector() == null) {
            insight.setEmbeddingVector(Collections.emptyList());
        }
        insight.setRequirementDensity(requirementDensity);
        return insight;
    }

    private double parseDensity(String response) {
        if (response == null) {
            throw new IllegalArgumentException("Requirement density response cannot be null.");
        }

        String cleaned = response.trim();
        if (cleaned.endsWith("%")) {
            cleaned = cleaned.substring(0, cleaned.length() - 1).trim();
        }
        cleaned = cleaned.replaceAll("[^0-9+\\-Ee.]+", "");

        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException ex) {
            log.error("Failed to parse numeric density from AI response: '{}'", response, ex);
            throw new IllegalStateException("Unable to parse requirement density from AI response.", ex);
        }
    }

    private List<Float> prepareQueryEmbedding(String query) {
        float[] embeddings = embeddingModel.embed(query);

        List<Float> embeddingList = new ArrayList<>(embeddings.length);
        for (float value : embeddings) {
            embeddingList.add(value);
        }
        return embeddingList;
    }

    private String formatContext(RegulatoryInsight insight) {
        return "Agency: " + insight.getAgencyName() + "\n" +
               "Compliance risk level: " + insight.getComplianceRiskLevel() + "\n" +
               "Summary: " + insight.getSummary();
    }
}