package com.phosa.cmas.util;

import java.util.Random;

public class RandomGeneratorUtil {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String DIGITS = "0123456789";
    private static final Random RANDOM = new Random();

    // Generates a random string of the specified length from the given character set
    public static String generateRandomString(int length, String characterSet) {
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            result.append(characterSet.charAt(RANDOM.nextInt(characterSet.length())));
        }
        return result.toString();
    }

    // Generates a random string of the specified length from the alphanumeric character set
    public static String generateRandomAlphanumeric(int length) {
        return generateRandomString(length, CHARACTERS);
    }

    // Generates a random numeric string of the specified length
    public static String generateRandomNumeric(int length) {
        return generateRandomString(length, DIGITS);
    }

    // Generates a six-digit verification code
    public static String generateVerificationCode() {
        return generateRandomNumeric(6);
    }

}

