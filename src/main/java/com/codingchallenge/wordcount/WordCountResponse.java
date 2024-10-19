package com.codingchallenge.wordcount;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WordCountResponse {
    private int byteCount;
    private int wordCount;
    private int lineCount;
    private int characterCount;
    private String filePath;
}
