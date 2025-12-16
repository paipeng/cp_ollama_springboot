package com.paipeng.ollama.util;

import com.paipeng.ollama.model.Vtt;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VttUtil {
    public static List<String> readResource(Resource resource) {
        /*
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read resource file to string", e);
        }

         */

        try {
            // Get the Path from the resource URL
            Path path = resource.getFile().toPath();

            // Read all lines using Files.readAllLines (returns a List<String>)
            return Files.readAllLines(path);

        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read resource file to string array by line", e);
        }
    }

    public static List<Vtt> parseVtt(List<String> lines) {
        List<Vtt> vtts = new ArrayList<>();


        Vtt vtt = null;
        for (String line : lines) {
            if (line.isBlank() || line.equals("WEBVTT") || line.startsWith("Kind:") || line.startsWith("Language:")) {
                continue;
            }
            if (isTimestamp(line)) {
                if (vtt == null) {
                    vtt = new Vtt(line);
                } else {
                    vtts.add(vtt);
                    vtt = new Vtt(line);
                }
            } else {
                if (vtt != null) {
                    vtt.setText(line);
                    vtts.add(vtt);
                    vtt = null;
                }
            }
        }
        return vtts;
    }

    public static List<Vtt> readVttFile(Resource resource) {
        List<String> lines = readResource(resource);
        return parseVtt(lines);
    }

    public static boolean isTimestamp(String text) {
        //String line = "00:00:00.600 --> 00:00:02.160";

        // Pattern explanation:
        // \d{2}   : two digits
        // :       : literal colon
        // \.      : literal dot
        String regex = ".*\\d{2}:\\d{2}:\\d{2}\\.\\d{3}.*";

        if (text.matches(regex)) {
            System.out.println("The line contains a valid timestamp.");
            return true;
        } else {
            return false;
        }
    }
}
