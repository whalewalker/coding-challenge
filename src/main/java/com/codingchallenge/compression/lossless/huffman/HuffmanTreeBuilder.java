package com.codingchallenge.compression.lossless.huffman;

import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTreeBuilder {
    public static Node buildTree(Map<Character, Integer> frequencies) {
        PriorityQueue<Node> queue = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            queue.add(node);
        }

        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();

            long frequency = left.getFrequency() + right.getFrequency();
            Node parent = new Node(frequency, left, right);

            queue.add(parent);
        }
        return queue.poll();
    }
}
