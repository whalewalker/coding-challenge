package com.codingchallenge.jsonparser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {
    private String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    @ParameterizedTest
    @CsvSource({
            "src/main/resources/tests/tests/step1/valid.json, 0",
            "src/main/resources/tests/tests/step1/invalid.json, 1",
            "src/main/resources/tests/tests/step2/valid.json, 0",
            "src/main/resources/tests/tests/step2/valid2.json, 0",
            "src/main/resources/tests/tests/step2/invalid.json, 1",
            "src/main/resources/tests/tests/step2/invalid2.json, 1",
            "src/main/resources/tests/tests/step3/valid.json, 0",
            "src/main/resources/tests/tests/step3/invalid.json, 1",
            "src/main/resources/tests/tests/step4/valid2.json, 0",
            "src/main/resources/tests/tests/step4/valid2.json, 0",
            "src/main/resources/tests/tests/step4/invalid.json, 1",
    })
    void testJsonParsing(String filePath, int expected) throws IOException {
        String input = readFile(filePath);
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        assertEquals(expected, parser.parse());
    }
}