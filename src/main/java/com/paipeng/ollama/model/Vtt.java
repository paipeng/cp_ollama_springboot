package com.paipeng.ollama.model;

import lombok.Data;

@Data
public class Vtt {
    private String timestamp;
    private String text;


    public Vtt() {

    }
    public Vtt(String timestamp) {
        this.timestamp = timestamp;
    }
}
