package com.back;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calc {
    public static int run(String content) {
        String[] str = content.split(" ");
        String [] new_str = FirstSearch(str);

        return search(new_str, "+");
    }

    private static String[] FirstSearch(String[] str) {
        List<String> new_str = new ArrayList<>();
        // 괄호 먼저 계산
        int start = -1;
        int end;
        boolean isFirst = false;
        boolean isMinus = false;

        for (int i = 0; i < str.length; i++) {
            if (str[i].contains("(")) { // 괄호 시작
                if (str[i].contains("-")) isMinus = true;
                start = i;
                isFirst = true;
            } else if (str[i].contains(")")) {
                end = i;
                new_str.add((isMinus ? "-" : "") + search(Arrays.copyOfRange(str, start, end + 1), "+"));
                start = -1;
                isFirst = false;
                isMinus = false;
            } else {
                if (!isFirst) new_str.add(str[i]);
            }
        }

        return new_str.toArray(new String[0]);
    }

    private static int search(String[] str, String op) {
        String b = "+"; // 연산자
        int sum = Integer.parseInt(filter(str[0])) * (op.equals("-") ? -1 : 1);

        for (int i = 1; i < str.length; i++) {
            String s = filter(str[i]);
            if (s.matches("[+\\-*]")) {
                b = s;
            } else if (s.matches("-?\\d+")) {
                if (b.equals("+") || b.equals("-")) {
                    sum = calculate(sum, "+", search(Arrays.copyOfRange(str, i, str.length), b));
                    break;
                } else if (b.equals("*")) {
                    sum = calculate(sum, b, Integer.parseInt(s));
                }
            }
        }

        return sum;
    }

    private static String filter(String s) {
        if (s.contains("(")) s = s.replace("-", "");
        s = s.replaceAll("[()]", "");
        return s;
    }

    private static int calculate(int a, String b, int c) {
        return switch (b) {
            case "+" -> a + c;
            case "*" -> a * c;
            default -> a;
        };
    }
}
