package com.codingchallenge.jsonparser;

import com.codingchallenge.jsonparser.exception.JsonParserException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Lexer {
    private String input;
    private int position;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (position < input.length()) {
            char current = input.charAt(position);

            if (Character.isWhitespace(current)) {
                position++;
            } else if (isSingleCharacterToken(current)) {
                tokens.add(createSingleCharacterToken(current));
                position++;
            } else if (current == '"') {
                tokens.add(tokenizeString());
            } else if (Character.isLetter(current)) {
                tokens.add(tokenizeIdentifier());
            } else if (Character.isDigit(current)) {
                tokens.add(tokenizeNumber());
            } else {
                throw new JsonParserException("Unknown character: " + current);
            }
        }

        tokens.add(new Token(Token.Type.EOF, ""));
        return tokens;
    }

    private boolean isSingleCharacterToken(char current) {
        return switch (current) {
            case '{', '}', '[', ']', ':', ',' -> true;
            default -> false;
        };
    }

    private Token createSingleCharacterToken(char current) {
        return switch (current) {
            case '{' -> new Token(Token.Type.LBRACE, "{");
            case '}' -> new Token(Token.Type.RBRACE, "}");
            case '[' -> new Token(Token.Type.LBRACKET, "[");
            case ']' -> new Token(Token.Type.RBRACKET, "]");
            case ':' -> new Token(Token.Type.COLON, ":");
            case ',' -> new Token(Token.Type.COMMA, ",");
            default -> throw new JsonParserException("Unknown character: " + current);
        };
    }

    private Token tokenizeString() {
        position++;
        StringBuilder string = new StringBuilder();
        while (position < input.length() && input.charAt(position) != '"') {
            string.append(input.charAt(position));
            position++;
        }
        if (position >= input.length()) {
            throw new JsonParserException("Unterminated string");
        }
        position++;
        return new Token(Token.Type.STRING, string.toString());
    }

    private Token tokenizeIdentifier() {
        StringBuilder identifier = new StringBuilder();
        while (position < input.length() && Character.isLetterOrDigit(input.charAt(position))) {
            identifier.append(input.charAt(position));
            position++;
        }
        return new Token(Token.Type.IDENTIFIER, identifier.toString());
    }

    private Token tokenizeNumber() {
        StringBuilder number = new StringBuilder();
        while (position < input.length() && Character.isDigit(input.charAt(position))) {
            number.append(input.charAt(position));
            position++;
        }
        return new Token(Token.Type.NUMBER, number.toString());
    }

    public static void main(String[] args) {
        Lexer lexer = new Lexer("{ \"null\": false }");
        List<Token> tokens = lexer.tokenize();
        for (Token token : tokens) {
            System.out.println(token.toString());
        }
    }
}