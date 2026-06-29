package com.mollanegash.regulator.service;

import com.mollanegash.regulator.model.RegulatoryAnalysis;
import com.mollanegash.regulator.model.RegulatoryInsight;
import com.mollanegash.regulator.repository.RegulatoryAnalysisRepository;
import com.mollanegash.regulator.repository.RegulatoryInsightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegulatoryIntelligenceServiceImpl implements RegulatoryIntelligenceService {

    private static final Logger log =
            LoggerFactory.getLogger(RegulatoryIntelligenceServiceImpl.class);

    private final RegulatoryInsightRepository insightRepository;
    private final RegulatoryAnalysisRepository analysisRepository;

    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;

    private final String openAiApiKey;
    private final String openAiModel;

    public RegulatoryIntelligenceServiceImpl(
            RegulatoryInsightRepository insightRepository,
            RegulatoryAnalysisRepository analysisRepository,
            ChatClient.Builder chatClientBuilder,
            EmbeddingModel embeddingModel,
            @Value("${spring.ai.openai.api-key:}") String openAiApiKey,
            @Value("${spring.ai.openai.chat.model:gpt-4o-mini}") String openAiModel
    ) {
        this.insightRepository = insightRepository;
        this.analysisRepository = analysisRepository;
        this.embeddingModel = embeddingModel;

        this.chatClient = chatClientBuilder
                .defaultSystem("You are a strict regulatory AI. Respond ONLY with a numeric percentage (example: 42.5). No words.")
                .build();

        this.openAiApiKey = openAiApiKey;
        this.openAiModel = openAiModel;
    }

    @Override
    @Transactional
    public String getRegulatoryInsight(String query) {

        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("Query cannot be empty");
        }

        try {
            List<Float> embedding = prepareQueryEmbedding(query);
            String embeddingJson = toEmbeddingJson(embedding);

            List<RegulatoryInsight> similar =
                    insightRepository.findTop5ByEmbeddingSimilarity(embeddingJson);

            String context = buildContext(similar);

            String aiResponse = requestAi(context, query);
            double density = parseDensity(aiResponse);

            analysisRepository.save(new RegulatoryAnalysis(
                    query,
                    aiResponse,
                    density,
                    similar.isEmpty() ? null : similar.get(0),
                    null
            ));

            return String.format("%.2f", density);

        } catch (RuntimeException ex) {
            log.error("AI pipeline failed", ex);
            return "0.00";
        }
    }

    private List<Float> prepareQueryEmbedding(String query) {
        float[] embeddings = embeddingModel.embed(query);

        List<Float> result = new java.util.ArrayList<>(embeddings.length);
        for (float v : embeddings) {
            result.add(v);
        }

        return result;
    }

    private String requestAi(String context, String query) {

        return chatClient.prompt()
                .system(s -> s.text(
                        "You are a regulatory scoring engine. " +
                        "Use context and return ONLY a number like 0-100. No text."
                ))
                .user(u -> u.text(
                        "Context:\n{context}\n\nQuery:\n{query}"
                )
                        .param("context", context)
                        .param("query", query))
                .call()
                .content();
    }

    private String toEmbeddingJson(List<Float> embedding) {
        return "[" + embedding.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) + "]";
    }

    private String buildContext(List<RegulatoryInsight> insights) {
        if (insights == null || insights.isEmpty()) {
            return "No context available.";
        }

        return insights.stream()
                .map(i -> i.getAgencyName() + " | " + i.getSummary())
                .collect(Collectors.joining("\n"));
    }

    private double parseDensity(String response) {
        if (response == null || response.isBlank()) {
            throw new IllegalStateException("Empty AI response");
        }

        String cleaned = response.replaceAll("[^0-9.]", "");

        if (cleaned.isBlank()) {
            throw new IllegalStateException("Cannot parse AI response: " + response);
        }

        return Double.parseDouble(cleaned);
    }

    @Override
    public RegulatoryAnalysis performAnalysis(String agencyName) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}