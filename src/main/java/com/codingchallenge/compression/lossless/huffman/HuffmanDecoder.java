package com.codingchallenge.compression.lossless.huffman;


public class HuffmanDecoder {
    public static String decode(byte[] compressedData, Node root, int totalBits) {
        if (root == null) return "";
        StringBuilder decodedText = new StringBuilder();

        // Handle the special case of a single unique character
        if (root.isLeaf()) {
            // Append the character for each valid bit in the encoded data
            char singleCharacter = root.getCharacter();
            decodedText.append(String.valueOf(singleCharacter).repeat(Math.max(0, totalBits)));
            return decodedText.toString();
        }

        Node currentNode = root;

        // Track the total number of bits processed
        int processedBits = 0;

        for (byte byteData : compressedData) {
            for (int bitIndex = 7; bitIndex >= 0; bitIndex--) {
                // Stop processing if all valid bits are decoded
                if (processedBits >= totalBits) {
                    return decodedText.toString();
                }

                // Extract the current bit (0 or 1)
                int currentBit = (byteData >> bitIndex) & 1;

                // Traverse the Huffman tree based on the bit
                currentNode = (currentBit == 0) ? currentNode.getLeft() : currentNode.getRight();

                // If we reach a leaf node, append the character to the result
                if (currentNode.isLeaf()) {
                    decodedText.append(currentNode.getCharacter());
                    currentNode = root; // Reset to the root for the next character
                }

                // Increment the bit counter
                processedBits++;
            }
        }

        return decodedText.toString();
    }

}
