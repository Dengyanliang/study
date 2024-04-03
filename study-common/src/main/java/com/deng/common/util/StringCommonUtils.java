package com.deng.common.util;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringCommonUtils
 */
public class StringCommonUtils {

    /**
     * 取字符串后1位
     */
    public static String tail1(String s) {
        s = "0" + s;
        String str = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                str = c + str;
            }
            if (str.length() == 1) {
                break;
            }
        }
        return str;
    }

    /**
     * 取字符串后2位
     */
    public static String tail2(String s) {
        s = "00" + s;
        String str = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                str = c + str;
            }
            if (str.length() == 2) {
                break;
            }
        }
        return str;
    }

    /**
     * 取字符串后4位
     */
    public static String tail4(String s) {
        s = "0000" + s;
        String str = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                str = c + str;
            }
            if (str.length() == 4) {
                break;
            }
        }
        return str;
    }

    /**
     * 拼接25位字符串序列
     */
    public static String tail25(String s) {
        StringBuffer sdf = new StringBuffer();
        for (int i = 1; i <= (25 - s.length()); i++) {
            sdf.append("0");
        }
        sdf.append(s);
        return sdf.toString();
    }

    /**
     * 取字符串后3位
     */
    public static String tail3(String s) {
        s = "000" + s;
        String str = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                str = c + str;
            }
            if (str.length() == 3) {
                break;
            }
        }
        return str;
    }

    public static boolean validAmt(String amount) {
        // 金额正则,可以没有小数，小数最多不超过两位
        String reg_money = "\\d+(\\.\\d{1,2})?";
        Pattern pattern = Pattern.compile(reg_money);
        Matcher matcher = pattern.matcher(amount);
        return matcher.matches();
    }

    /**
     * 根据编码类型获得签名内容byte[]
     */
    public static byte[] getContentBytesByUTF8(String content) {
        return content.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 取模后的绝对值
     *
     * @param value      传入的值
     * @param modCount   取模的数量
     */
    public static String getAbsModuloValue(int value, int modCount) {
        return String.valueOf(Math.abs(value % modCount));
    }

    /**
     * 取模后的值
     *
     * @param value      传入的值
     * @param modCount   取模的数量
     */
    public static String getModuloValue(long value, int modCount) {
        return String.valueOf(value % modCount);
    }


}
