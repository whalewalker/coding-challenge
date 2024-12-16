package com.codingchallenge.compression.lossless.huffman;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HuffmanTreeBuilderTest {
    @Test
    void testEmptyFrequencies() {
        Map<Character, Integer> frequencies = new HashMap<>();
        Node root = HuffmanTreeBuilder.buildTree(frequencies);
        assertNull(root);
    }

    @Test
    void testSingleCharacter() {
        Map<Character, Integer> frequencies = new HashMap<>();
        frequencies.put('a', 5);
        Node root = HuffmanTreeBuilder.buildTree(frequencies);
        assertNotNull(root);
        assertEquals('a', root.getCharacter());
        assertEquals(5, root.getFrequency());
    }

    @Test
    void testTwoCharacters() {
        Map<Character, Integer> frequencies = new HashMap<>();
        frequencies.put('a', 5);
        frequencies.put('b', 9);
        Node root = HuffmanTreeBuilder.buildTree(frequencies);
        assertNotNull(root);
        assertEquals(14, root.getFrequency());
        assertEquals('\0', root.getCharacter());
        assertNotNull(root.getLeft());
        assertNotNull(root.getRight());
    }

    @Test
    void testMultipleCharacters() {
        Map<Character, Integer> frequencies = new HashMap<>();
        frequencies.put('a', 5);
        frequencies.put('b', 9);
        frequencies.put('c', 12);
        frequencies.put('d', 13);
        frequencies.put('e', 16);
        frequencies.put('f', 45);
        Node root = HuffmanTreeBuilder.buildTree(frequencies);
        assertNotNull(root);
        assertEquals(100, root.getFrequency());
    }

    @Test
    void testCharactersWithSameFrequency() {
        Map<Character, Integer> frequencies = new HashMap<>();
        frequencies.put('a', 5);
        frequencies.put('b', 5);
        frequencies.put('c', 5);
        Node root = HuffmanTreeBuilder.buildTree(frequencies);
        assertNotNull(root);
        assertEquals(15, root.getFrequency());
    }

    @Test
    void testLargeFrequencies() {
        Map<Character, Integer> frequencies = new HashMap<>();
        frequencies.put('a', Integer.MAX_VALUE);
        frequencies.put('b', Integer.MAX_VALUE);
        Node root = HuffmanTreeBuilder.buildTree(frequencies);
        assertNotNull(root);
        assertEquals((long) Integer.MAX_VALUE * 2, root.getFrequency());
    }
}