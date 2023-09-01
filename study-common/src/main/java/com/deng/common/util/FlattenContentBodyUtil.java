package com.deng.common.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Desc:内容处理工具
 * @Auther: dengyanliang
 * @Date: 2020/10/16 11:16
 */
@Slf4j
public class FlattenContentBodyUtil {

    private static final int ONE_K = 1024;
    private static final int ONE_M = ONE_K * 1024;
    private static final List<String> flattenSymbolList = Lists.newArrayList("\"", "\\\\");

    /**
     * 校验内容是否超限
     * @param contentId
     * @param originContent
     * @return
     */
    public static boolean checkExceeded(Long contentId, String originContent){
        String flattenContentBody = originContent.replaceAll("\\s+", " ");
        // 去掉一些特殊符号，避免在传输过程中，增加转移字符，导致文体过大，导致mafka无法发送
        for (String symbol : flattenSymbolList) {
            flattenContentBody = flattenContentBody.replaceAll(symbol, "");
        }

        Float maxSize = 10.0f;
        int maxByteSize = (int) (maxSize * ONE_M); // 多少M
        byte[] bodyBytes = null;
        try {
            bodyBytes = flattenContentBody.getBytes("utf-8");
        } catch (Exception e) {
            log.warn("获取打平后数据字节数错误", e);
            bodyBytes = flattenContentBody.getBytes();
        }

        if (bodyBytes.length >= maxByteSize) {
            log.warn("contentId:{},打平后数据大小:{},大于限制大小:{}", contentId, bodyBytes.length, maxByteSize);
            return true;
        }
        return false;
    }
}
