package com.back;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calc {
    public static int run(String content) {
        String[] tokens = tokenize(content);
        tokens = firstSearch(tokens);
        return search(tokens);
    }

    private static String[] tokenize(String content) {
        content = content.replace("-(", "-1 * ( ")
                .replace("(", " ( ")
                .replace(")", " ) ");

        return content.trim().split("\\s+");
    }

    private static String[] firstSearch(String[] tokens) {
        List<String> result = new ArrayList<>();

        int depth = 0;
        int startIdx = -1;

        for (int i = 0; i < tokens.length; i++) {

            if (tokens[i].equals("(")) {
                if (depth == 0) startIdx = i;
                depth++;
            } else if (tokens[i].equals(")")) {
                depth--;

                if (depth == 0) {
                    String[] inner = Arrays.copyOfRange(tokens, startIdx + 1, i);
                    inner = firstSearch(inner);

                    int value = search(inner);
                    result.add(String.valueOf(value));
                }
            } else if (depth == 0) {
                result.add(tokens[i]);
            }
        }

        return result.toArray(new String[0]);
    }

    private static int search(String[] str) {
        int result = 0;
        int current = Integer.parseInt(str[0]);
        int sign = 1;

        for (int i = 1; i < str.length; i++) {
            String token = str[i];

            switch (token) {
                case "+" -> {
                    result += sign * current;
                    sign = 1;
                    current = 0;
                }
                case "-" -> {
                    result += sign * current;
                    sign = -1;
                    current = 0;
                }
                case "*" -> {
                    i++;
                    current *= Integer.parseInt(str[i]);
                }
                default -> current = Integer.parseInt(token);
            }
        }

        result += sign * current;
        return result;
    }
}
