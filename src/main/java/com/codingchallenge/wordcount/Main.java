package com.codingchallenge.wordcount;

import java.util.Scanner;

public class Main {
   public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String inputLine;

    while (true) {
        System.out.println("Enter the command and file name (or 'q' to quit): ");
        inputLine = scanner.nextLine();

        if (inputLine.equalsIgnoreCase("q")) {
            break;
        }

        String[] input = inputLine.split(" ");
        WordCount wc = new WordCount();
        try {
            WordCountResponse response = wc.wordCount(input);
            System.out.println(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
}