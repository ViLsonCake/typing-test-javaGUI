package com.project.validate;

public class Validator {
    public static String deleteExtraCharacters(String word) {
        if (word.trim().split(" ").length == 1)
            return word;

        String [] wordWithExtraCharacters = word.trim().split(" ");

        return wordWithExtraCharacters[0];
    }
}
