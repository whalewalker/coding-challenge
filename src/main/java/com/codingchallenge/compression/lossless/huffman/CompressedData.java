package com.codingchallenge.compression.lossless.huffman;

public record CompressedData(byte[] bytes, Node root, int bitCount) {}
