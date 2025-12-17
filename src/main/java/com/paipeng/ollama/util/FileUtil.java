package com.paipeng.ollama.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileUtil {
    public static Path writeFile(Path filePath, String data) {

        try {
            // Write the string content to the file
            Files.writeString(filePath, data, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Successfully wrote to the file: " + filePath.toAbsolutePath());
            return filePath.toAbsolutePath();
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
            return null;
        }
    }
}
