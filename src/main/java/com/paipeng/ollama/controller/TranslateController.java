package com.paipeng.ollama.controller;

import com.paipeng.ollama.config.ApplicationConfig;
import com.paipeng.ollama.model.TranslateRequest;
import com.paipeng.ollama.model.Vtt;
import com.paipeng.ollama.service.FileStorageService;
import com.paipeng.ollama.service.TranslateService;
import com.paipeng.ollama.util.VttUtil;
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
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping(value = "/translate")
public class TranslateController {
    private final Logger logger = LoggerFactory.getLogger(TranslateController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private TranslateService translateService;


    @PostMapping(value = "/cn", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ChatResponse generate(@RequestBody TranslateRequest translateRequest) {
        logger.info("generate: " + translateRequest.getText());

        return translateService.translate(translateRequest);
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path targetLocation = fileStorageService.getFileStoragePath().resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            response.put("fileName", fileName);
            response.put("fileSize", file.getSize());
            response.put("message", "File uploaded successfully!");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("error", "Could not upload the file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/upload2")
    public ResponseEntity<Resource> uploadFile2(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path targetLocation = fileStorageService.getFileStoragePath().resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Path filePath = fileStorageService.getFileStoragePath().resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());


            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/upload3")
    public ResponseEntity<ChatResponse> uploadFile3(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path targetLocation = fileStorageService.getFileStoragePath().resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Path filePath = fileStorageService.getFileStoragePath().resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());


            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            List<Vtt> vtts = VttUtil.readVttFile(resource);
            String fullText = VttUtil.getFullText(vtts);

            TranslateRequest translateRequest = new TranslateRequest();
            translateRequest.setPrompt("Translate German to Chinese");
            translateRequest.setText(fullText);


            ChatResponse chatResponse = translateService.translate(translateRequest);

            return ResponseEntity.ok()
                    //.contentType(MediaType.APPLICATION_JSON)
                    .body(chatResponse);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/vtt")
    public ResponseEntity<Resource> uploadVttFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path targetLocation = fileStorageService.getFileStoragePath().resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Path filePath = fileStorageService.getFileStoragePath().resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());


            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            TranslateRequest translateRequest = new TranslateRequest();
            translateRequest.setPrompt("Translate German to Chinese");

            List<Vtt> vtts = VttUtil.readVttFile(resource);
            for (Vtt vtt : vtts) {
                logger.info("vtt: " + vtt.getTimestamp() + " -> " + vtt.getText());
                if (vtt.getText() != null) {
                    translateRequest.setText(vtt.getText());
                    ChatResponse chatResponse = translateService.translate(translateRequest);
                    vtt.setText(chatResponse.getResult().getOutput().getText());
                }
            }

            String vttContent = VttUtil.convertVttToString(vtts);

            // save vtts to file
            Path translatedFilePath = fileStorageService.writeVttFile(filePath.toString(), vttContent);
            resource = new UrlResource(translatedFilePath.toUri());


            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
