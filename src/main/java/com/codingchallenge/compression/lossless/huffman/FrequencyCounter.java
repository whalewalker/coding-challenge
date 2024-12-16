package com.codingchallenge.compression.lossless.huffman;

import java.util.HashMap;
import java.util.Map;

public class FrequencyCounter {
    public static Map<Character, Integer> countFrequencies(String input) {
        Map<Character, Integer> frequencies = new HashMap<>();

        for (char c : input.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }
}
