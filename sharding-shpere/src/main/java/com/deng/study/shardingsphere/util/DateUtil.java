package com.deng.study.shardingsphere.util;


import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Desc: 如果传入的是String,只有按照传入的日期格式进行转化，才能格式化成功
 * 注意：注意日期中 日期字母大小写会造成不一样的结果
 *
 * @author
 * @date 2017/11/13
 */
@Slf4j
public class DateUtil {

    public static final String YMD_HMS = "yyyyMMddHHmmss";
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD_MAX = "yyyy-MM-dd 23:59:59";
    public static final String YMD_MIN = "yyyy-MM-dd 00:00:00";
    public static final String YMD = "yyyy-MM-dd";
    public static final String MAX_DATE = "2099-12-31 23:59:59";
    public static final String YM = "yyyyMM";
    public static final String Y_M = "yyyy-MM";
    public static final String YYYY_MM_DD_HH_mm_ss2 = "yyyy/MM/dd HH:mm:ss";
    public static final String YYYY_MM_DD2 = "yyyy.MM.dd";


    public static void main(String[] args) {
        Timestamp sqlDate = getSqlDate();
        System.out.println(sqlDate);
    }

    public static String getDateNow() {
        Date now = new Date();
        //创建格式化对象
        SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS);
        //格式化对象 d
        return sdf.format(now);
    }

    public static Timestamp getSqlDate()  {
        try {
            Date now = new Date();
            //创建格式化对象
            SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            //格式化对象 d
            String strdf1 = sdf.format(now);

            Date date = sdf.parse(strdf1);
            //初始化一个sql.Date对象为setDate()作准备
            //为sql.Date对象赋值

            Timestamp timestamp = new Timestamp(date.getTime());
            System.out.println(timestamp);
            return timestamp;
//
//            java.sql.Date date = new java.sql.Date(parse.getTime());
//            System.out.println(date);
//            return date;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
