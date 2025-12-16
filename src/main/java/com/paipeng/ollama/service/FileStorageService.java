package com.paipeng.ollama.service;

import com.paipeng.ollama.config.ApplicationConfig;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    @Autowired
    private ApplicationConfig applicationConfig;

    private Path fileStoragePath;

    @PostConstruct
    public void init() {
        try {
            fileStoragePath = Paths.get(applicationConfig.getUploadDir()).toAbsolutePath().normalize();
            Files.createDirectories(fileStoragePath);
            logger.info("Upload directory initialized at: {}", fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public Path getFileStoragePath() {
        return fileStoragePath;
    }
}