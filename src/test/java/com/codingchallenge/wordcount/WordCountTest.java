package com.codingchallenge.wordcount;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.*;

class WordCountTest {

    @ParameterizedTest
    @CsvSource({
            "'-c', 'testfile.txt', 'Hello World', 11, 0, 0, 0",
            "'-c', 'emptyfile.txt', '', 0, 0, 0, 0",
            "'-c', 'specialcharsfile.txt', 'Hello, 世界!', 14, 0, 0, 0",
            "'-c', 'multilinefile.txt', 'Line1\nLine2\nLine3', 17, 0, 0, 0",
            "'-l', 'testfile.txt', 'Hello\nWorld', 0, 2, 0, 0",
            "'-l', 'emptyfile.txt', '', 0, 0, 0, 0",
            "'-l', 'specialcharsfile.txt', 'Hello, 世界!\nこんにちは', 0, 2, 0, 0",
            "'-l', 'multilinefile.txt', 'Line1\nLine2\nLine3\nLine4', 0, 4, 0, 0",
            "'-w', 'testfile.txt', 'Hello World', 0, 0, 2, 0",
            "'-w', 'emptyfile.txt', '', 0, 0, 0, 0",
            "'-w', 'specialcharsfile.txt', 'Hello, 世界!', 0, 0, 2, 0",
            "'-w', 'multilinefile.txt', 'Line1\nLine2\nLine3', 0, 0, 3, 0",
            "'-m', 'testfile.txt', 'Hello World', 0, 0, 0, 11",
            "'-m', 'emptyfile.txt', '', 0, 0, 0, 0",
            "'-m', 'specialcharsfile.txt', 'Hello, 世界!', 0, 0, 0, 10",
            "'-m', 'multilinefile.txt', 'Line1\nLine2\nLine3', 0, 0, 0, 15",
            "'' , 'testfile.txt', 'Hello World', 11, 1, 2, 0",
            "'' , 'emptyfile.txt', '', 0, 0, 0, 0",
            "'' , 'specialcharsfile.txt', 'Hello, 世界!', 14, 1, 2, 0",
            "'' , 'multilinefile.txt', 'Line1\nLine2\nLine3', 17, 3, 3, 0"
    })
    void givenFile_whenCounting_thenCorrectCountReturned(String cmd, String fileName, String content, int expectedByteCount, int expectedLineCount, int expectedWordCount, int expectedCharacterCount, @TempDir Path tempDir) throws Exception {
        Path tempFile = tempDir.resolve(fileName);
        Files.write(tempFile, content.getBytes(), StandardOpenOption.CREATE);

        String[] args = cmd.isEmpty() ? new String[]{tempFile.toString()} : new String[]{cmd, tempFile.toString()};

        WordCount wc = new WordCount();

        WordCountResponse response = wc.wordCount(args);
        switch (cmd) {
            case "-c" ->
                    assertEquals(expectedByteCount, response.getByteCount(), "The byte count did not match the expected value.");
            case "-l" ->
                    assertEquals(expectedLineCount, response.getLineCount(), "The line count did not match the expected value.");
            case "-w" ->
                    assertEquals(expectedWordCount, response.getWordCount(), "The word count did not match the expected value.");
            case "-m" ->
                    assertEquals(expectedCharacterCount, response.getCharacterCount(), "The character count did not match the expected value.");
            default -> {
                assertEquals(expectedByteCount, response.getByteCount(), "The byte count did not match the expected value.");
                assertEquals(expectedLineCount, response.getLineCount(), "The line count did not match the expected value.");
                assertEquals(expectedWordCount, response.getWordCount(), "The word count did not match the expected value.");
            }
        }
    }

    @Test
    void givenInvalidCommandLineArgs_whenCounting_thenThrowsException() {
        String[] args = {"-y", "src/main/java/com/codingchallenge/wordcount/testfile.txt"};

        WordCount wc = new WordCount();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> wc.wordCount(args));

        assertEquals("Invalid arguments. Usage: ccwc -y <filename>", exception.getMessage());
    }

    @Test
    void givenNonExistentFile_whenCounting_thenThrowsException() {
        String[] args = {"-c", "src/main/java/com/codingchallenge/wordcount/nonexistentfile.txt"};

        WordCount wc = new WordCount();

        assertThrows(NoSuchFileException.class,
                () -> wc.wordCount(args));
    }

}