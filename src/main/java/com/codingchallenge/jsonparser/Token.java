package com.codingchallenge.jsonparser;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {
    private Type type;
    private String value;
    enum Type {
        STRING,
        NUMBER,
        COMMA,
        COLON,
        LBRACE,
        RBRACE,
        LBRACKET,
        RBRACKET,
        IDENTIFIER,
        EOF,
    }


}
