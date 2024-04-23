package com.deng.common.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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

    public static final String YMD_MAX_2 = "yyyyMMdd235959";
    public static final String YMD_MIN_2 = "yyyyMMdd000000";

    public static final String Y_M = "yyyy-MM";
    public static final String Y_M_D = "yyyy-MM-dd";
    public static final String MAX_DATE = "2099-12-31 23:59:59";
    public static final String YM = "yyyyMM";
    public static final String YMD = "yyyyMMdd";
    public static final String YYYY_MM_DD_HH_mm_ss2 = "yyyy/MM/dd HH:mm:ss";
    public static final String YYYY_MM_DD2 = "yyyy.MM.dd";



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

    public static String getDateNow() {
        Date now = new Date();
        //创建格式化对象
        SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS);
        //格式化对象 d
        return sdf.format(now);
    }

    public static String getDate() {
        Date now = new Date();
        //创建格式化对象
        SimpleDateFormat sdf = new SimpleDateFormat(Y_M_D);
        //格式化对象 d
        return sdf.format(now);
    }

    public static Timestamp getSqlDate2()  {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public static void main(String[] args) throws ParseException {

//        SimpleDateFormat format = new SimpleDateFormat(YMD_MAX);
//        String result = format.parse(new Date());
//        System.out.println(result);


        Date dateMax = toDateMax(new Date());
//        System.out.println(dateMax);

        /* ************** 将字符串转化为日期 begin *******************/
        // 把字符串转化为日期的时候，只能转化为同格式的
        String dateParam = "20230403";
        SimpleDateFormat FORMAT_YMD = new SimpleDateFormat(YMD);
        Date date = FORMAT_YMD.parse(dateParam);
        System.out.println(date);

        SimpleDateFormat FORMAT_YMD_MIN = new SimpleDateFormat(YMD_MIN);
        String beginTime = FORMAT_YMD_MIN.format(date);
        System.out.println(beginTime);
//        System.out.println(FORMAT_YMD_MIN.parse(beginTime).getTime());


        SimpleDateFormat FORMAT_YMD_MAX = new SimpleDateFormat(YMD_MAX);
        String endTime = FORMAT_YMD_MAX.format(date);
        System.out.println(endTime);
        String t1 = "2023-04-03 10:59:59";

        System.out.println(StringUtils.compare(beginTime, t1));
        System.out.println(StringUtils.compare(endTime, t1));

//        System.out.println(FORMAT_YMD_MAX.parse(endTime));
//        System.out.println(FORMAT_YMD_MAX.parse(endTime).getTime());



        /* ************** 将字符串转化为日期 end *******************/

//        Date result3 = format2.parse(result2);
//        System.out.println(result3);

//
//        Date parse = dateFormat.parse(time);
//        System.out.println("parse = " + parse);

        // 创建两个Calendar对象
//        Calendar calendar1 = Calendar.getInstance();
//        Calendar calendar2 = Calendar.getInstance();
//
//        // 设置日期
//        calendar1.set(2014, Calendar, 24); // 2021年12月31日
//        calendar2.set(2013, Calendar.MINUTE, 3); // 2022年1月1日
//
//        // 将Calendar对象转换为Date对象
//        Date date1 = calendar1.getTime();
//        Date date2 = calendar2.getTime();
//
//        // 计算日期差值（以天为单位）
//        long difference = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
//
//        // 输出结果
//        System.out.println("两个日期的差值为：" + difference + "天");
    }


}
