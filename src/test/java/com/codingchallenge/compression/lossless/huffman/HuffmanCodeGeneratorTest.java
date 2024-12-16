package com.codingchallenge.compression.lossless.huffman;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HuffmanCodeGeneratorTest {

    @Test
    void testEmptyTree() {
        Node root = null;
        Map<Character, String> codes = HuffmanCodeGenerator.generateCodes(root);
        assertTrue(codes.isEmpty());
    }

    @Test
    void testSingleCharacterTree() {
        Node root = new Node('a', 5);
        Map<Character, String> codes = HuffmanCodeGenerator.generateCodes(root);
        assertEquals(1, codes.size());
        assertEquals("", codes.get('a'));
    }

    @Test
    void testTwoCharacterTree() {
        Node left = new Node('a', 5);
        Node right = new Node('b', 9);
        Node root = new Node(14, left, right);
        Map<Character, String> codes = HuffmanCodeGenerator.generateCodes(root);
        assertEquals(2, codes.size());
        assertEquals("0", codes.get('a'));
        assertEquals("1", codes.get('b'));
    }

    @Test
    void testMultipleCharacterTree() {
        Node left = new Node('a', 5);
        Node right = new Node('b', 9);
        Node root = new Node(14, left, right);
        Node left2 = new Node('c', 12);
        Node right2 = new Node(14, root, left2);
        Node root2 = new Node(26, right2, new Node('d', 13));
        Map<Character, String> codes = HuffmanCodeGenerator.generateCodes(root2);
        assertEquals(4, codes.size());
        assertEquals("10", codes.get('a'));
        assertEquals("11", codes.get('b'));
        assertEquals("0", codes.get('c'));
        assertEquals("1", codes.get('d'));
    }

    @Test
    void testCharactersWithSameFrequency() {
        Node left = new Node('a', 5);
        Node right = new Node('b', 5);
        Node root = new Node(10, left, right);
        Map<Character, String> codes = HuffmanCodeGenerator.generateCodes(root);
        assertEquals(2, codes.size());
        assertEquals("0", codes.get('a'));
        assertEquals("1", codes.get('b'));
    }

    @Test
    void testLargeTree() {
        Node left = new Node('a', 5);
        Node right = new Node('b', 9);
        Node root = new Node(14, left, right);
        Node left2 = new Node('c', 12);
        Node right2 = new Node(14, root, left2);
        Node root2 = new Node(26, right2, new Node('d', 13));
        Node left3 = new Node('e', 16);
        Node right3 = new Node(42, root2, left3);
        Node root3 = new Node(58, right3, new Node('f', 45));
        Map<Character, String> codes = HuffmanCodeGenerator.generateCodes(root3);
        assertEquals(6, codes.size());
        assertEquals("110", codes.get('a'));
        assertEquals("111", codes.get('b'));
        assertEquals("10", codes.get('c'));
        assertEquals("0", codes.get('d'));
        assertEquals("01", codes.get('e'));
        assertEquals("1", codes.get('f'));
    }

    @Test
    void testFile() throws IOException {
        String filePath = "src/main/resources/tests/test.txt";
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        Map<Character, Integer> frequencies = FrequencyCounter.countFrequencies(content);
        Node root = HuffmanTreeBuilder.buildTree(frequencies);
        Map<Character, String> codes = HuffmanCodeGenerator.generateCodes(root);
        System.out.println(codes);
    }
}