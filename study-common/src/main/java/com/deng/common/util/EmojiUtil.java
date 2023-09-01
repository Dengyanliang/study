package com.deng.common.util;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fuwuzhou
 * @version 1.0
 * @created 16/7/14
 */
public class EmojiUtil {

    public static final String EMOJI_STR = "[^(⺀-\u9FFF\\w\\s`~!@#\\$%\\^&\\*\\(\\)_+-？（）——=\\[\\]{}\\|;。，、《》”：；“！……’:'\"<,>\\.?/\\\\*)]";


    public static final String EMOJI_REG = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
    public static final Pattern PATTERN = Pattern.compile("<:([[-]\\d*[,]]+):>");

     /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean hasEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }

        Pattern pattern = Pattern.compile(EMOJI_REG);
        Matcher matcher = pattern.matcher(source);
        return matcher.find();
    }


    public static String filterEmoji(String str) {
        if (Strings.isNullOrEmpty(str)) {
            return "";
        }

        Pattern pattern = Pattern.compile(EMOJI_STR);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb2 = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb2, "");
        }
        matcher.appendTail(sb2);
        return Strings.isNullOrEmpty(sb2.toString()) ? "默认主题" : sb2.toString();
    }

    /**
     * 将str中的emoji表情转为byte数组
     */
    public static String resolveToByteFromEmoji(String str) {
        if (Strings.isNullOrEmpty(str)) {
            return "";
        }

        Pattern pattern = Pattern.compile(EMOJI_STR);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb2 = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb2, resolveToByte(matcher.group(0)));
        }
        matcher.appendTail(sb2);
        return sb2.toString();
    }

    /**
     * 将str中的byte数组类型的emoji表情转为正常显示的emoji表情
     */
    public static String resolveToEmojiFromByte(String str) {
        if (Strings.isNullOrEmpty(str)) {
            return "";
        }

        Matcher matcher2 = PATTERN.matcher(str);
        StringBuffer sb3 = new StringBuffer();
        while (matcher2.find()) {
            matcher2.appendReplacement(sb3, resolveToEmoji(matcher2.group(0)));
        }
        matcher2.appendTail(sb3);
        return sb3.toString();
    }

    private static String resolveToByte(String str) {
        byte[] b = str.getBytes();
        StringBuilder sb = new StringBuilder();
        sb.append("<:");
        for (int i = 0; i < b.length; i++) {
            if (i < b.length - 1) {
                sb.append(Byte.valueOf(b[i]).toString()).append(",");
            } else {
                sb.append(Byte.valueOf(b[i]).toString());
            }
        }
        sb.append(":>");
        return sb.toString();
    }

    private static String resolveToEmoji(String str) {
        str = str.replaceAll("<:", "").replaceAll(":>", "");
        String[] s = str.split(",");
        byte[] b = new byte[s.length];
        for (int i = 0; i < s.length; i++) {
            b[i] = Byte.valueOf(s[i]);
        }
        return new String(b);
    }


}
