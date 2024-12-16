package com.codingchallenge.compression.lossless.huffman;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class HuffmanEncoderTest {

    @Test
    void testEmptyString() {
        CompressedData compressedData = HuffmanEncoder.encode("");
        assertFalse(compressedData.bytes().length > 0);
    }

    @Test
    void testSingleCharacter() {
        CompressedData compressedData = HuffmanEncoder.encode("a");
        assertNotNull(compressedData);
        assertTrue(compressedData.bytes().length > 0);
    }

    @Test
    void testRepeatedCharacter() {
        CompressedData compressedData = HuffmanEncoder.encode("aaaaaa");
        assertNotNull(compressedData);
        assertTrue(compressedData.bytes().length > 0);
    }

    @Test
    void testMultipleCharacters() {
        CompressedData compressedData = HuffmanEncoder.encode("abc");
        assertNotNull(compressedData);
        assertFalse(compressedData.bytes().length > 0);
    }

    @Test
    void testMixedCharacters() {
        CompressedData compressedData = HuffmanEncoder.encode("aabbcc");
        assertNotNull(compressedData);
        assertTrue(compressedData.bytes().length > 0);
    }

    @Test
    void testSpecialCharacters() {
        CompressedData compressedData = HuffmanEncoder.encode("a!b@c#");
        assertNotNull(compressedData);
        assertTrue(compressedData.bytes().length > 0);
    }

    @Test
    void testWhitespaceCharacters() {
        CompressedData compressedData = HuffmanEncoder.encode("a b c");
        assertNotNull(compressedData);
        assertTrue(compressedData.bytes().length > 0);
    }

    @Test
    void testUnicodeCharacters() {
        CompressedData compressedData = HuffmanEncoder.encode("你好");
        assertNotNull(compressedData);
        assertTrue(compressedData.bytes().length > 0);
    }

    @Test
    void testLongString() {
        String input = "a".repeat(1000) + "b".repeat(500) + "c".repeat(250);
        CompressedData compressedData = HuffmanEncoder.encode(input);
        assertNotNull(compressedData);
        assertTrue(compressedData.bytes().length > 0);
    }

    @Test
    void testFile() throws IOException {
        String filePath = "src/main/resources/tests/test.txt";
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        CompressedData compressedData = HuffmanEncoder.encode(content);
        System.out.println(compressedData.bytes());
    }
}