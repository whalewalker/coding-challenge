package com.codingchallenge.jsonparser;

import com.codingchallenge.jsonparser.exception.JsonParserException;
import lombok.Data;

import java.util.List;
import java.util.NoSuchElementException;

@Data
public class Parser {
    private Lexer lexer;
    private TokenIterator tokenIterator;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public int parse() {
        try {
            List<Token> tokens = lexer.tokenize();
            System.out.println(tokens);
            tokenIterator = new TokenIterator(tokens);
           return parseValue();
        } catch (Exception e) {
            return 1;
        }

    }

    private int parseValue() {
        Token token = tokenIterator.next();

        return switch (token.getType()) {
            case LBRACE -> parseObject();
            case LBRACKET -> parseArray();
            case STRING, IDENTIFIER, NUMBER, BOOLEAN, NULL -> 0;
            default -> 1;
        };
    }


    private int parseArray() {
        int sum = 0;
        while (tokenIterator.hasNext()) {
            Token token = tokenIterator.next();
            if (token.getType() == Token.Type.RBRACKET) {
                return sum > 0 ? 1 : 0;
            }
            if (token.getType() != Token.Type.COMMA) {
                tokenIterator.previous();
                sum += parseValue();
            }
        }
        throw new NoSuchElementException("Expected closing bracket for array");
    }

    private int parseObject() {
        int sum = 0;
        while (tokenIterator.hasNext()) {
            Token token = tokenIterator.next();
            if (token.getType() == Token.Type.RBRACE) {
                return sum > 0 ? 1 : 0;
            }
            if (token.getType() == Token.Type.STRING) {
                tokenIterator.next();
                sum += parseValue();
            }
        }
        throw new NoSuchElementException("Expected closing brace for object");
    }

    private static class TokenIterator {
        private final List<Token> tokens;
        private int position;

        public TokenIterator(List<Token> tokens) {
            this.tokens = tokens;
            this.position = 0;
        }

        public boolean hasNext() {
            return position < tokens.size();
        }

        public Token next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return tokens.get(position++);
        }

        public void previous() {
            if (position > 0) {
                position--;
            }
        }
    }

    public static void main(String[] args) {
        String input = """
                {
                  "a": 1,
                  "b": [1, 2, 3],
                  "c": {
                    "d": 4,
                    "e": [5, 6]
                  }
                }
                """;
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        System.out.println(parser.parse());
    }
}