package com.paipeng.ollama.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VttUtilTest {
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
}
