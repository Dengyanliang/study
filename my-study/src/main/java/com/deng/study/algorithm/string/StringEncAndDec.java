package com.deng.study.algorithm.string;

/**
 * @Desc:字符串加密和解密
 *
 *  输入：
 *      abcdefg
 *  输出：
 *      BCDEFGH
 *
 *  输入：
 *      BCDEFGH
 *  输出：
 *      abcdefg
 *
 * @Auther: dengyanliang
 * @Date: 2023/1/31 17:36
 */
public class StringEncAndDec {

    private static char[] SMALL_LETTER = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static char[] CAPITAL_LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static char[] NUMBER = "0123456789".toCharArray();


    public static void main(String[] args) {
        String str = "B5WWIj56vu72GzRja7j5"; // 输出 BCDEFGH3
//        String str = "BCDEFGH3";
        String encrypt = encrypt(str);
        System.out.println("加密："+encrypt);

        String decrypt = decrypt(encrypt);
        System.out.println("解密："+decrypt);

        String decrypt2 = decrypt2(encrypt);
        System.out.println("解密："+decrypt2);
    }

    private static String encrypt(String input){
        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int ascii = (int)chars[i];      // 求出当前字符对应的ascii码
//            System.out.println("当前字符："+chars[i]+"，ascii：" + ascii);

            if(chars[i] >= 'a' && chars[i] <= 'z'){
                result.append(CAPITAL_LETTER[(ascii - 97 + 1) % 26]); // 字母a的ascii码是97，这里减成0再加1，然后再去大写数组里找对应下标就是了
            }else if(chars[i] >= 'A' && chars[i] <= 'Z'){
                result.append(SMALL_LETTER[(ascii - 65 + 1) % 26]);
            }else if(chars[i] >= '0' && chars[i] <= '9'){
                result.append(NUMBER[(ascii - 48 + 1) % 10]);
            }else{
                result.append(chars[i]);
            }
        }
        return result.toString();
    }

    private static String decrypt(String output){
        StringBuilder result = new StringBuilder();
        char[] chars = output.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int ascii = (int)chars[i];
            if(chars[i] >= 'a' && chars[i] <= 'z'){
                result.append(CAPITAL_LETTER[(ascii - 97 + 26 - 1) % 26]);  // 字母a的ascii码是97，为了防止负数出现，我们加26后再取模
            }else if(chars[i] >= 'A' && chars[i] <= 'Z'){
                result.append(SMALL_LETTER[(ascii - 65 + 26 - 1) % 26]);
            }else if(chars[i] >= '0' && chars[i] <= '9'){
                result.append(NUMBER[(ascii - 48 + 10 - 1) % 10]);
            }else{
                result.append(chars[i]);
            }
        }

        return result.toString();
    }

    private static String decrypt2(String input) {
        char[] arr = input.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            int index = (int)arr[i];
            if (arr[i] >= 'a' && arr[i] <= 'z') {
                builder.append(CAPITAL_LETTER[(index - 97 + 26 - 1) % 26]); // 字母a的int值是97,为了防止负数出现,我们加26后再取模.PS:取模就跟拨时钟一样,12个钟多拨一圈会回到原点;)
            } else if (arr[i] >= 'A' && arr[i] <= 'Z') {
                builder.append(SMALL_LETTER[(index - 65 + 26 - 1) % 26]);
            } else if (arr[i] >= '0' && arr[i] <= '9') {
                builder.append(NUMBER[(index - 48 + 10 - 1) % 10]);
            } else {
                builder.append(arr[i]);
            }
        }
        return builder.toString();
    }
}
