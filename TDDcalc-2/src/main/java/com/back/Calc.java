package com.back;

import java.util.Arrays;

public class Calc {
    public static int run(String content) {
        String[] str = content.split(" ");
        return search(str, "+");
    }

    private static int search(String[] str, String op) {
        int sum = Integer.parseInt(str[0]) * (op.equals("-") ? -1 : 1);
        String b = "+"; // 연산자

        for (int i = 1; i < str.length; i++) {
            if (str[i].matches("[+\\-*]")) {
                b = str[i];
            } else if (str[i].matches("-?\\d+")) {
                if (b.equals("+") || b.equals("-")) {
                    sum = calculate(sum, "+", search(Arrays.copyOfRange(str, i, str.length), b));
                    break;
                } else if (b.equals("*")) {
                    sum = calculate(sum, b, Integer.parseInt(str[i]));
                }
            }
        }

        return sum;
    }

    private static int calculate(int a, String b, int c) {
        return switch (b) {
            case "+" -> a + c;
            case "-" -> a - c;
            case "*" -> a * c;
            default -> a;
        };
    }
}
