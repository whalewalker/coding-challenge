package com.codingchallenge.wordcount;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class WordCount {
    private static final String ERROR_MSG = "Invalid arguments. Usage: ccwc %s <filename>";
    private static final List<String> WORD_COUNT_CMD = List.of("-c", "-l", "-w", "-m");

    public WordCountResponse wordCount(String... args) throws Exception {
        if (args == null || args.length < 1) {
            throw new IllegalArgumentException(String.format(ERROR_MSG, "<command>"));
        }

        String cmd = args.length > 1 ? args[0] : "";
        String filePath = args.length > 1 ? args[1] : args[0];

        validateFilePath(filePath);

        Path path = Path.of(filePath);
        int byteCount = 0;
        int lineCount = 0;
        int wordCount = 0;
        int characterCount = 0;

        if (cmd.isEmpty()) {
            byteCount = countBytes(path);
            lineCount = countLines(path);
            wordCount = countWords(path);
        } else {
            validateCommandLineArgs(cmd);
            switch (cmd) {
                case "-c" -> byteCount = countBytes(path);
                case "-l" -> lineCount = countLines(path);
                case "-w" -> wordCount = countWords(path);
                case "-m" -> characterCount = countCharacters(path);
                default -> throw new IllegalArgumentException(String.format(ERROR_MSG, cmd));
            }
        }

        return WordCountResponse.builder()
                .byteCount(byteCount)
                .lineCount(lineCount)
                .wordCount(wordCount)
                .characterCount(characterCount)
                .filePath(path.getFileName().toString())
                .build();
    }


    private int countCharacters(Path path) throws IOException {
        return Files.readAllLines(path).stream()
                .mapToInt(String::length)
                .sum();
    }

    private int countWords(Path path) throws IOException {
        return Files.readAllLines(path).stream()
                .mapToInt(line -> line.split("\\s+").length)
                .sum();
    }

    private void validateFilePath(String filePath) {
        if (isBlank(filePath)) {
            throw new IllegalArgumentException(String.format(ERROR_MSG, "<command>"));
        }
    }

    private void validateCommandLineArgs(String cmd) {
        if (isBlank(cmd) || !WORD_COUNT_CMD.contains(cmd)) {
            throw new IllegalArgumentException(String.format(ERROR_MSG, cmd));
        }
    }

    private int countBytes(Path path) throws Exception {
        return Files.readAllBytes(path).length;
    }

    private int countLines(Path path) throws Exception {
        return Files.readAllLines(path).size();
    }

    private boolean isBlank(String str) {
        return str == null || str.isBlank();
    }
}
