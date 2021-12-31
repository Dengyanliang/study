package com.deng.study.java;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/10/10 16:14
 */
@Slf4j
public class StringTest {

    public static final String TITLE_LENGTH_EXCEEDED_MESSAGE = "标题已超过%s字限制，请适当缩减"; // 对于final修饰的字符串，也可以使用%s进行修饰

    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String ss = String.format(TITLE_LENGTH_EXCEEDED_MESSAGE, 10);
        System.out.println(ss);

        StringBuffer messageBuffer = new StringBuffer(500);
        if (StringUtils.isBlank(messageBuffer.toString())) {
            System.out.println("11111");
        }
        long elapsed = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        System.out.println("elapsed:" +elapsed);

        logInfo("orderId:%s orderPurchaseAndOccupyStockComplete isLocator statusResult：%s,test:%s",1L,"nihao","ok");

//        String logMessage = String.format("orderId:%s orderPurchaseAndOccupyStockComplete isLocator statusResult", 1);
//        log.info(logMessage);
    }


    public static void logInfo(String msg,Long orderId,Object... object){
        log.info("----------------------");
        String logMessage = String.format(msg, orderId, Arrays.toString(object));
        log.info(logMessage);
        log.info("**********************");
    }

}
