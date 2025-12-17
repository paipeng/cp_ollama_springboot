package com.paipeng.ollama.service;

import com.paipeng.ollama.model.TranslateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TranslateServiceTest {
    @Autowired
    TranslateService translateService;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void translate() {
        assertEquals(42, Integer.sum(19, 23));

        TranslateRequest translateRequest = new TranslateRequest();
        translateRequest.setPrompt("Translate German to Chinese");
        translateRequest.setText("Guten Tag");


        ChatResponse chatResponse = translateService.translate(translateRequest);

        System.out.println("chatResponse: " + chatResponse.getResult().toString());
    }
}