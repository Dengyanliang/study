package com.deng.common.util;


import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

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
        java.sql.Date sqlDate = getSqlDate();
        System.out.println(sqlDate);
    }

    /**
     * 该方法是通用方法，支持上面定义的所有格式
     * @param date
     * @param pattern
     * @return
     */
    public static String parseDateFormat(Date date,String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String retVal = "0000-00-00";
        try {
            retVal = sdf.format(date);
        } catch (Exception e) {
            log.error("Date转换错误，date:  {} ", date.toString(), e);
        }

        return retVal;
    }



    public static Date toDateMax(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(YMD_MAX);
        SimpleDateFormat format1 = new SimpleDateFormat(YMDHMS);
        String result = format.format(date);
        try {
            return format1.parse(result);
        } catch (ParseException e) {
            log.info("转化日期出错,date = {}", date);
            return null;
        }
    }


    public static Date getMaxDateForThisMonth(Date date) {
        Date now = org.apache.commons.lang3.time.DateUtils.ceiling(date, Calendar.MONTH);
        return org.apache.commons.lang3.time.DateUtils.addSeconds(now, -1);
    }

    public static Date getMinDateForThisMonth(Date date) {
        return org.apache.commons.lang3.time.DateUtils.truncate(date, Calendar.MONTH);
    }

    /**
     * 以当前日期为基准，按月份偏移，取最大日日期。
     */
    public static Date getMaxDateForMonthRange(Date date, int effset) {
        Date max = getMaxDateForThisMonth(date);
        return org.apache.commons.lang3.time.DateUtils.addMonths(max, effset);
    }

    /**
     * 以当前日期为基准，按月份偏移，取最小日日期。
     */
    public static Date getMinDateForMonthRange(Date date, int effset) {
        Date min = getMinDateForThisMonth(date);
        return org.apache.commons.lang3.time.DateUtils.addMonths(min, effset);
    }

    public static Date now() {
        return new Date();
    }

    /**
     * 将long类型的毫秒数转化为Date
     *
     * @param millisSecond 毫秒
     * @return
     */
    public static Date convertToDate(long millisSecond) {
        return new Date(millisSecond);
    }

    public static String format(Date date, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return dateTimeFormatter.format(localDateTime);
    }


    public static Date addMonth(Date date,int addMonth)  {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MONTH, addMonth);
        return rightNow.getTime();
    }

    public static java.sql.Date getSqlDate()  {
        Date now = new Date();
        //创建格式化对象
        SimpleDateFormat formatter = new SimpleDateFormat(YMD_HMS);
        //格式化对象 d
        String strdf1=formatter.format(now);
        System.out.println(strdf1);

        //初始化一个sql.Date对象为setDate()作准备
        //为sql.Date对象赋值
        java.sql.Date date = new java.sql.Date(now.getTime());
        return date;
    }


}
