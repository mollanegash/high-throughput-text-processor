package com.mollanegash.regulator.service;

import com.mollanegash.regulator.model.RegulatoryInsight;
import com.mollanegash.regulator.repository.RegulatoryInsightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegulatoryIntelligenceServiceTest {

    @Mock
    private RegulatoryInsightRepository vectorRepository;

    @Mock
    private ChatClient.Builder chatClientBuilder;

    @Mock
    private ChatClient chatClient;

    @Mock
    private EmbeddingModel embeddingModel;

    private RegulatoryIntelligenceServiceImpl intelligenceService;

    @BeforeEach
    void setUp() {
        when(chatClientBuilder.defaultSystem(anyString())).thenReturn(chatClientBuilder);
        when(chatClientBuilder.build()).thenReturn(chatClient);
        intelligenceService = new RegulatoryIntelligenceServiceImpl(vectorRepository, chatClientBuilder, embeddingModel);
    }

    @Test
    void testGetRegulatoryInsight_ReturnsResponse() {
        RegulatoryInsight insight = mock(RegulatoryInsight.class);
        when(insight.getAgencyName()).thenReturn("EPA");
        when(insight.getComplianceRiskLevel()).thenReturn("High");
        when(insight.getSummary()).thenReturn("This is important regulatory context.");

        when(vectorRepository.findTopByEmbeddingSimilarity(anyList()))
                .thenReturn(Optional.of(insight));

        when(embeddingModel.embed(anyString())).thenReturn(new float[]{0.1f, 0.2f, 0.3f});

        ChatClient.ChatClientRequestSpec mockRequestSpec = mock(ChatClient.ChatClientRequestSpec.class);
        ChatClient.CallResponseSpec mockResponseSpec = mock(ChatClient.CallResponseSpec.class);

        when(chatClient.prompt()).thenReturn(mockRequestSpec);
        when(mockRequestSpec.system(any(Consumer.class))).thenReturn(mockRequestSpec);
        when(mockRequestSpec.user(any(Consumer.class))).thenReturn(mockRequestSpec);
        when(mockRequestSpec.call()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.content()).thenReturn("42.0");

        String result = intelligenceService.getRegulatoryInsight("What is the regulatory risk?");

        assertEquals("42.00", result);
        verify(vectorRepository, atLeastOnce()).findTopByEmbeddingSimilarity(anyList());
    }
}
