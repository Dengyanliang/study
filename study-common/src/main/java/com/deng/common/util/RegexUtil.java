package com.deng.common.util;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liyiwen on 2017/09/21.
 */
public class RegexUtil {
    private static final Pattern URL_PATTERN = Pattern.compile("[a-zA-z]+(://)?[^s|^\u4e00-\u9fa5]*");

    public static boolean isUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        Matcher m = URL_PATTERN.matcher(url.trim());
        return m.matches();
    }

    private static final Pattern FORMAT_PATTERN = Pattern.compile("/([\\-0-9]+\\.?[E0-9]*([\\-0-9,]+\\.?[E0-9]*)*)");

    public static String formatUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return "";
        }

        url = url.replaceFirst("^[a-zA-z]+(://)", "")
                .replaceFirst("\\?(.*)$", "")
                .replaceAll("(%([0-9abcdef]){2})+", "x");

        Matcher m = FORMAT_PATTERN.matcher(url.trim());
        while (m.find()) {
            url = url.replace(m.group(1), "x");
        }
        return url.replaceAll("/", "_");
    }
}
