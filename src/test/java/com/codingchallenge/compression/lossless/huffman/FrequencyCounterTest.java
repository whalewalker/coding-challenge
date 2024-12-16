package com.codingchallenge.compression.lossless.huffman;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FrequencyCounterTest {

    @Test
    void testEmptyString() {
        Map<Character, Integer> frequencies = FrequencyCounter.countFrequencies("");
        assertTrue(frequencies.isEmpty());
    }

    @Test
    void testSingleCharacter() {
        Map<Character, Integer> frequencies = FrequencyCounter.countFrequencies("a");
        assertEquals(1, frequencies.size());
        assertEquals(1, frequencies.get('a'));
    }

    @Test
    void testRepeatedCharacter() {
        Map<Character, Integer> frequencies = FrequencyCounter.countFrequencies("aaa");
        assertEquals(1, frequencies.size());
        assertEquals(3, frequencies.get('a'));
    }

    @Test
    void testMultipleCharacters() {
        Map<Character, Integer> frequencies = FrequencyCounter.countFrequencies("abc");
        assertEquals(3, frequencies.size());
        assertEquals(1, frequencies.get('a'));
        assertEquals(1, frequencies.get('b'));
        assertEquals(1, frequencies.get('c'));
    }

    @Test
    void testMixedCharacters() {
        Map<Character, Integer> frequencies = FrequencyCounter.countFrequencies("aabbcc");
        assertEquals(3, frequencies.size());
        assertEquals(2, frequencies.get('a'));
        assertEquals(2, frequencies.get('b'));
        assertEquals(2, frequencies.get('c'));
    }

    @Test
    void testSpecialCharacters() {
        Map<Character, Integer> frequencies = FrequencyCounter.countFrequencies("a!b@c#");
        assertEquals(6, frequencies.size());
        assertEquals(1, frequencies.get('a'));
        assertEquals(1, frequencies.get('!'));
        assertEquals(1, frequencies.get('b'));
        assertEquals(1, frequencies.get('@'));
        assertEquals(1, frequencies.get('c'));
        assertEquals(1, frequencies.get('#'));
    }

    @Test
    void testWhitespaceCharacters() {
        Map<Character, Integer> frequencies = FrequencyCounter.countFrequencies("a b c");
        assertEquals(4, frequencies.size());
        assertEquals(1, frequencies.get('a'));
        assertEquals(1, frequencies.get('b'));
        assertEquals(2, frequencies.get(' '));
        assertEquals(1, frequencies.get('c'));
    }

    @Test
    void testUnicodeCharacters() {
        Map<Character, Integer> frequencies = FrequencyCounter.countFrequencies("你好");
        assertEquals(2, frequencies.size());
        assertEquals(1, frequencies.get('你'));
        assertEquals(1, frequencies.get('好'));
    }

    @Test
    void testLongString() {
        String input = "a".repeat(1000) + "b".repeat(500) + "c".repeat(250);
        Map<Character, Integer> frequencies = FrequencyCounter.countFrequencies(input);
        assertEquals(3, frequencies.size());
        assertEquals(1000, frequencies.get('a'));
        assertEquals(500, frequencies.get('b'));
        assertEquals(250, frequencies.get('c'));
    }
}