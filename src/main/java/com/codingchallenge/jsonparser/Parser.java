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
            case STRING, NUMBER -> 0;
            default -> {
                if (isBoolean(token.getValue()) || isNull(token.getValue())) {
                    yield 0;
                } else {
                    throw new JsonParserException("Unexpected token: " + token.getValue());
                }
            }
        };
    }


    private int parseArray() {
        int sum = 1;
        while (tokenIterator.hasNext()) {
            Token token = tokenIterator.next();
            if (token.getType() == Token.Type.RBRACKET)
                return sum > 0 ? 1 : 0;
            if (token.getType() != Token.Type.COMMA) {
                tokenIterator.previous();
                sum += parseValue();
            }
        }
        throw new NoSuchElementException("Expected closing bracket for array");
    }

    private int parseObject() {
        int sum = 0;
        boolean expectKey = true;
        while (tokenIterator.hasNext()) {
            Token token = tokenIterator.next();
            if (token.getType() == Token.Type.RBRACE) {
                return handleClosingBrace(sum);
            }
            if (token.getType() == Token.Type.COMMA) {
                if (expectKey) throw new JsonParserException("Unexpected comma in object");
                expectKey = true;
            } else {
                handleKeyValue(expectKey);
                sum += parseKeyValue();
                expectKey = false;
            }
        }
        throw new NoSuchElementException("Expected closing brace for object");
    }

    private int handleClosingBrace(int sum) {
        tokenIterator.previous();
        tokenIterator.previous();
        Token token = tokenIterator.next();
        if (token.getType() == Token.Type.COMMA) throw new JsonParserException("Unexpected comma after closing brace");
        return sum > 0 ? 1 : 0;
    }

    private void handleKeyValue(boolean expectKey) {
        if (!expectKey) {
            throw new JsonParserException("Expected comma between key-value pairs");
        }
        tokenIterator.previous();
    }

    private int parseKeyValue() {
        Token keyToken = tokenIterator.next();
        if (keyToken.getType() != Token.Type.STRING) {
            throw new JsonParserException("Expected string as key in object");
        }
        Token colonToken = tokenIterator.next();
        if (colonToken.getType() != Token.Type.COLON) {
            throw new JsonParserException("Expected colon after key in object");
        }
        return parseValue();
    }

    private boolean isBoolean(String identifier) {
        return identifier.equals("true") || identifier.equals("false");
    }

    private boolean isNull(String identifier) {
        return identifier.equals("null");
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
                }
                
                
                """;
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        System.out.println(parser.parse());
    }
}