package com.codingchallenge.compression.lossless.huffman;

import lombok.Data;

@Data
public class Node implements Comparable<Node>{
    private char character;
    private long frequency;
    private Node left;
    private Node right;

    public Node(char character, long frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    public Node(long frequency, Node left, Node right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
        this.character = '\0';
    }

    @Override
    public int compareTo(Node other) {
        return Long.compare(frequency, other.frequency);
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public String toString() {
        return "Node[character=" + character + ", frequency=" + frequency + "]";
    }
}
