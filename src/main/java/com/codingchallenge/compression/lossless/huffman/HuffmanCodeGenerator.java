package com.codingchallenge.compression.lossless.huffman;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class HuffmanCodeGenerator {
    public static Map<Character, String> generateCodes(Node root){
        if (root == null) return Collections.emptyMap();

        Map<Character, String> huffmanCodes = new HashMap<>();
        if (root.isLeaf()) {
            huffmanCodes.put(root.getCharacter(), "0");
            return huffmanCodes;
        }
        buildCodes(root, "", huffmanCodes);
        return huffmanCodes;
    }

    private static void buildCodes(Node node, String code, Map<Character, String> huffmanCodes) {
        if (node == null) return;

        if (node.getCharacter() != '\0')
            huffmanCodes.put(node.getCharacter(), code);

        buildCodes(node.getLeft(), format("%s0", code), huffmanCodes);
        buildCodes(node.getRight(), format("%s1", code), huffmanCodes);
    }
}
