package com.back;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calc {
    public static int run(String content) {
        content = content.replace("-(", "-1 * ( ").replace("(", " ( ")
                .replace(")", " ) ");
        String[] str = content.trim().split("\\s+");
        str = firstSearch(str);
        return search(str, "+");
    }

    private static String[] firstSearch(String[] str) {
        List<String> result = new ArrayList<>();

        int depth = 0;
        int start = -1;

        for (int i = 0; i < str.length; i++) {

            if (str[i].equals("(")) {
                if (depth == 0) start = i;
                depth++;
            }
            else if (str[i].equals(")")) {
                depth--;

                if (depth == 0) {
                    // 괄호 하나 완성
                    String[] inner = Arrays.copyOfRange(str, start + 1, i);

                    // 재귀로 내부 괄호 먼저 처리
                    inner = firstSearch(inner);

                    // 계산
                    int value = search(inner, "+");

                    // 결과 추가
                    result.add(String.valueOf(value));
                }
            }
            else {
                // 괄호 밖이면 그대로 추가
                if (depth == 0 && !str[i].isBlank()) {
                    result.add(str[i]);
                }
            }
        }

        return result.toArray(new String[0]);
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
            case "*" -> a * c;
            default -> a;
        };
    }
}
