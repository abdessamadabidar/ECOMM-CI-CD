package com.devops.shopino.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class StringGenerator {
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String ALL = UPPERCASE + NUMBERS;
    private static final SecureRandom random = new SecureRandom();


    private static String selectRandomTokens(int n, String tokens) {
        StringBuilder randomTokens = new StringBuilder();

        for (int i = 0; i < n; i++) {
            randomTokens.append(tokens.charAt(random.nextInt(tokens.length())));
        }
        return randomTokens.toString();
    }

    public static String generate(int nUppercase, int nNumbers, int maxlength) {
        int fill = maxlength - (nUppercase + nNumbers);
        if (fill < 0) {
            throw new IllegalArgumentException();
        }
        return shuffle(
                selectRandomTokens(nUppercase, UPPERCASE)
                        + selectRandomTokens(nNumbers, NUMBERS)
                        + selectRandomTokens(fill, ALL)
        );
    }

    private static String shuffle(String s) {
        List<String> tokens = Arrays.asList(s.split(""));
        Collections.shuffle(tokens);
        return String.join("", tokens);
    }
}
