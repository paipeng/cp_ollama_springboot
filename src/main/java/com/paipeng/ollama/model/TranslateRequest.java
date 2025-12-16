package com.paipeng.ollama.model;

import lombok.Data;

@Data
public class TranslateRequest {
    private String text;
    private String prompt;
}
