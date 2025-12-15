package com.paipeng.ollama.model;

import lombok.Data;

@Data
public class Version {
    private String time;
    private String sha256;
    private String projectDir;
    private String version;
    private String name;
    private String createDate;

}
