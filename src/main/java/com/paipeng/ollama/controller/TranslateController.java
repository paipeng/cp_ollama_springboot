package com.paipeng.ollama.controller;

import com.paipeng.ollama.model.TranslateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/translate")
public class TranslateController {
    private final Logger logger = LoggerFactory.getLogger(VersionController.class);
    @Autowired
    private OllamaChatModel chatModel;

    @PostMapping(value = "/cn", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ChatResponse generate(@RequestBody TranslateRequest translateRequest) {
        logger.info("generate: " + translateRequest.getText());

        SystemMessage systemMessage = new SystemMessage(translateRequest.getPrompt());
        UserMessage userMessage = new UserMessage(translateRequest.getText());

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage), OllamaChatOptions.builder()
                .temperature(0.4)
                .build());

        String template = """
            ### Instruction: {instruction}
            ### Input: {input_text}
            ### Response:
            """;

        // Create a PromptTemplate instance
        PromptTemplate promptTemplate = new PromptTemplate(template);


        Prompt prompt2 = promptTemplate.create(Map.of(
                "instruction", "Translate German to Chinese.",
                "input_text", translateRequest.getText()));
        return this.chatModel.call(prompt2);
    }


}
