package com.paipeng.ollama.util;

import com.paipeng.ollama.controller.VersionController;
import com.paipeng.ollama.model.Vtt;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VttUtilTest {
    private final Logger logger = LoggerFactory.getLogger(VttUtilTest.class);

    @Test
    public void testAdd() {
        assertEquals(42, Integer.sum(19, 23));
    }

    @Test
    void readResource() {
    }

    @Test
    void parseVtt() {
    }

    @Test
    void isTimestamp() {
        String line = "00:00:00.600 --> 00:00:02.160";
        assertTrue(VttUtil.isTimestamp(line));

        assertFalse(VttUtil.isTimestamp("WEBVTT"));
    }

    @Test
    void readVttFile() {
        URL url = VttUtilTest.class.getClassLoader().getResource("deutsch.vtt");
        assertNotNull(url);
        Resource resource = new UrlResource(url);
        List<Vtt> vtts = VttUtil.readVttFile(resource);
        assertNotNull(vtts);
        for (Vtt vtt : vtts) {
            System.out.println(vtt.getTimestamp() + " -> " + vtt.getText());
        }

        String fullText = VttUtil.getFullText(vtts);
        System.out.println(fullText);
    }
}
