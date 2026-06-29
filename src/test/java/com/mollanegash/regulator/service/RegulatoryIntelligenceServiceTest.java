package com.mollanegash.regulator.service;

import com.mollanegash.regulator.model.RegulatoryAnalysis;
import com.mollanegash.regulator.model.RegulatoryInsight;
import com.mollanegash.regulator.repository.RegulatoryAnalysisRepository;
import com.mollanegash.regulator.repository.RegulatoryInsightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegulatoryIntelligenceServiceTest {

    @Mock
    private RegulatoryInsightRepository insightRepository;

    @Mock
    private RegulatoryAnalysisRepository analysisRepository;

    @Mock
    private ChatClient.Builder chatClientBuilder;

    @Mock
    private ChatClient chatClient;

    @Mock
    private EmbeddingModel embeddingModel;

    private RegulatoryIntelligenceServiceImpl service;

    @BeforeEach
    void setUp() {

        when(chatClientBuilder.defaultSystem(anyString()))
                .thenReturn(chatClientBuilder);

        when(chatClientBuilder.build())
                .thenReturn(chatClient);

        service = new RegulatoryIntelligenceServiceImpl(
                insightRepository,
                analysisRepository,
                chatClientBuilder,
                embeddingModel,
                "test-key",
                "gpt-4o-mini"
        );
    }

    @Test
    void testGetRegulatoryInsight_ReturnsResponse() {

        RegulatoryInsight insight = mock(RegulatoryInsight.class);
        when(insight.getAgencyName()).thenReturn("EPA");
        when(insight.getComplianceRiskLevel()).thenReturn("High");
        when(insight.getSummary()).thenReturn("Context");

        when(insightRepository.findTop5ByEmbeddingSimilarity(anyString()))
                .thenReturn(List.of(insight));

        when(embeddingModel.embed(anyString()))
                .thenReturn(new float[]{0.1f, 0.2f, 0.3f});

        ChatClient.ChatClientRequestSpec request = mock(ChatClient.ChatClientRequestSpec.class);
        ChatClient.CallResponseSpec response = mock(ChatClient.CallResponseSpec.class);

        when(chatClient.prompt()).thenReturn(request);

        when(request.system(any(Consumer.class))).thenReturn(request);
        when(request.user(any(Consumer.class))).thenReturn(request);
        when(request.call()).thenReturn(response);
        when(response.content()).thenReturn("42.0");

        String result = service.getRegulatoryInsight("EPA rules");

        assertEquals("42.00", result);

        verify(analysisRepository).save(any(RegulatoryAnalysis.class));
    }

    @Test
    void testFallback() {

        when(insightRepository.findTop5ByEmbeddingSimilarity(anyString()))
                .thenReturn(List.of());

        when(embeddingModel.embed(anyString()))
                .thenReturn(new float[]{0.1f, 0.2f, 0.3f});

        ChatClient.ChatClientRequestSpec request = mock(ChatClient.ChatClientRequestSpec.class);
        ChatClient.CallResponseSpec response = mock(ChatClient.CallResponseSpec.class);

        when(chatClient.prompt()).thenReturn(request);
        when(request.system(any(Consumer.class))).thenReturn(request);
        when(request.user(any(Consumer.class))).thenReturn(request);
        when(request.call()).thenReturn(response);
        when(response.content()).thenReturn("0.0");

        String result = service.getRegulatoryInsight("test");

        assertEquals("0.00", result);
    }
}