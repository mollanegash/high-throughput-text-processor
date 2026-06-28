package com.mollanegash.regulator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;

@SpringBootTest
class FederalRegulationsAnalyzerApplicationTests {

    @MockBean
    private ChatClient chatClient;

    @MockBean
    private EmbeddingModel embeddingModel;

	@Test
	void contextLoads() {
	}

}
