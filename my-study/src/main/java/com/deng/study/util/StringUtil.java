package com.deng.study.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * String工具类
 *
 * @author songbo
 * @date 2017/8/3
 */
@Slf4j
public class StringUtil<main> {

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getStartDate(String dateTime) {
        return dateTime.split("-")[0];
    }

    public static String getStartTime(String dateTime) {
        return dateTime.split("-")[1];
    }



    public static String format4FullOrgName(String fullOrgName) {
        if (StringUtils.isNotBlank(fullOrgName)) {
            return fullOrgName.replaceAll("-", "/");
        }
        return null;
    }

    public static String encryptPhone(String phone) {
        return (StringUtils.isBlank(phone) || phone.length() != 11) ? phone : phone.substring(0, 3) + "********";
    }

    /**
     * 处理发表的内容
     * @param content
     * @return
     */
    public static String dealWithContent(String content){
        content = HtmlUtil.dealWithContent(content); // HTML标签处理
        content = HttpUtil.dealWithContent(content); // HttpUrl处理
        return content;
    }
}
