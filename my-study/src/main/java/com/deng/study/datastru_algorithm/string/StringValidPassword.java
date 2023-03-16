package com.deng.study.datastru_algorithm.string;

import java.util.Scanner;

/**
 * @Desc: 验证密码是否正确
 * @Auther: dengyanliang
 * @Date: 2023/1/31 20:20
 */
public class StringValidPassword {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String password = scanner.nextLine();
            char[] chars = password.toCharArray();

            if (password.length() > 8) {
                int a = 0, b = 0, d = 0, e = 0;
                boolean isError = false;
                for (char c : chars) {
                    if (c >= 'a' && c <= 'z') {
                        a = 1;
                    } else if (c >= 'A' && c <= 'Z') {
                        b = 1;
                    } else if (c >= '0' && c <= '9') {
                        d = 1;
                    } else if (c == ' ' || c == '\n') {
                        isError = true;
                    } else {
                        e = 1;
                    }
                }
                if (isError) {
                    System.out.println("NG");
                } else {
                    if ((a + b + d + e) >= 3) {
                        if (repeatStr(password)) {
                            System.out.println("OK");
                        } else {
                            System.out.println("NG");
                        }
                    } else {
                        System.out.println("NG");
                    }
                }
            } else {
                System.out.println("NG");
            }

        }

    }

    private static boolean repeatStr(String password) {
        for (int i = 3; i < password.length(); i++) {
            System.out.println(password.substring(i) + "," + password.substring(i - 3, i));
            if (password.substring(i).contains(password.substring(i - 3, i))) {
                return false;
            }
        }

        return true;
    }


}
