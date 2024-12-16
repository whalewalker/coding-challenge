package com.codingchallenge.compression.lossless.huffman;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HuffmanDecoderTest {
    @Test
    void testEmptyString() {
        CompressedData compressedData = HuffmanEncoder.encode("");
        String decoded = HuffmanDecoder.decode(compressedData.bytes(), compressedData.root(), compressedData.bitCount());
        assertEquals("", decoded);
    }

    @Test
    void testSingleCharacter() {
        CompressedData compressedData = HuffmanEncoder.encode("a");
        String decoded = HuffmanDecoder.decode(compressedData.bytes(), compressedData.root(), compressedData.bitCount());
        assertEquals("a", decoded);
    }

    @Test
    void testRepeatedCharacter() {
        CompressedData compressedData = HuffmanEncoder.encode("aaa");
        String decoded = HuffmanDecoder.decode(compressedData.bytes(), compressedData.root(), compressedData.bitCount());
        assertEquals("aaa", decoded);
    }

    @Test
    void testMultipleCharacters() {
        CompressedData compressedData = HuffmanEncoder.encode("ab");
        String decoded = HuffmanDecoder.decode(compressedData.bytes(), compressedData.root(), compressedData.bitCount());
        assertEquals("ab", decoded);
    }

    @Test
    void testMixedCharacters() {
        CompressedData compressedData = HuffmanEncoder.encode("abac");
        String decoded = HuffmanDecoder.decode(compressedData.bytes(), compressedData.root(), compressedData.bitCount());
        assertEquals("abac", decoded);
    }

    @Test
    void testSpecialCharacters() {
        CompressedData compressedData = HuffmanEncoder.encode("!#@");
        String decoded = HuffmanDecoder.decode(compressedData.bytes(), compressedData.root(), compressedData.bitCount());
        assertEquals("!#@", decoded);
    }

    @Test
    void testWhitespaceCharacters() {
        CompressedData compressedData = HuffmanEncoder.encode("ab ");
        String decoded = HuffmanDecoder.decode(compressedData.bytes(), compressedData.root(), compressedData.bitCount());
        assertEquals("ab ", decoded);
    }

    @Test
    void testUnicodeCharacters() {
        CompressedData compressedData = HuffmanEncoder.encode("你好");
        String decoded = HuffmanDecoder.decode(compressedData.bytes(), compressedData.root(), compressedData.bitCount());
        assertEquals("你好", decoded);
    }

    @Test
    void testLongString() {
        CompressedData compressedData = HuffmanEncoder.encode("a".repeat(1000) + "b".repeat(500) + "c".repeat(250));
        String decoded = HuffmanDecoder.decode(compressedData.bytes(), compressedData.root(), compressedData.bitCount());
        assertEquals("a".repeat(1000) + "b".repeat(500) + "c".repeat(250), decoded);
    }

    @Test
    void testFile() throws IOException {
        String filePath = "src/main/resources/tests/test.txt";
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        CompressedData compressedData = HuffmanEncoder.encode(content);
        System.out.println("compressedData bytes length (in bits): " + compressedData.bytes().length);

        String decoded = HuffmanDecoder.decode(compressedData.bytes(), compressedData.root(), compressedData.bitCount());
        System.out.println("Decoded bytes length (in bits): " + decoded.length() * 8);
        assertEquals(content, decoded);
    }
}