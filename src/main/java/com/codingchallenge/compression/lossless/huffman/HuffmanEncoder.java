package com.codingchallenge.compression.lossless.huffman;


import java.io.ByteArrayOutputStream;
import java.util.Map;

public class HuffmanEncoder {
    public static CompressedData encode(String text) {
        Node root = HuffmanTreeBuilder.buildTree(FrequencyCounter.countFrequencies(text));
        Map<Character, String> huffmanCodes = HuffmanCodeGenerator.generateCodes(root);
        StringBuilder encodedText = new StringBuilder();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int currentByte = 0;
        int bitCount = 0;

        for (char c : text.toCharArray()) {
            String code = huffmanCodes.get(c);
            encodedText.append(code);

            for (char bit : code.toCharArray()) {
                currentByte = (currentByte << 1) | (bit - '0');
                bitCount++;

                if (bitCount == 8) {
                    outputStream.write(currentByte);
                    currentByte = 0;
                    bitCount = 0;
                }
            }
        }

        if (bitCount > 0) {
            currentByte <<= (8 - bitCount);
            outputStream.write(currentByte);
        }

        return new CompressedData(outputStream.toByteArray(), root, encodedText.length());
    }
}
