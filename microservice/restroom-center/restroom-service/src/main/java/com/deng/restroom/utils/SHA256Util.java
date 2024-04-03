package com.deng.restroom.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.deng.common.util.StringCommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * SHA256 签名算法
 */
@Slf4j
public class SHA256Util {
    private SHA256Util(){}
    //签名类型
    private static final String SIGN_TYPE = "HmacSHA256";

    /**
     * 根据参数生成签名
     *
     * @param appId     appid
     * @param signKey   签名密钥
     * @param timestamp 时间戳
     * @param body      数据（按ASCII表的顺序排序）
     * @return
     * @throws Exception
     */
    public static String getSign(String appId, String signKey, String timestamp, String body) throws Exception {
        log.debug("signKey:{},timestamp:{}", signKey, timestamp);
        String jsonString = JSON.toJSONString(JSON.parseObject(body), SerializerFeature.MapSortField);
        byte[] data = StringCommonUtils.getContentBytesByUTF8(jsonString);
        InputStream is = new ByteArrayInputStream(data);
        String testSH = DigestUtils.sha256Hex(is);
        String s1 = appId + timestamp + testSH;
        Mac mac = Mac.getInstance(SIGN_TYPE);
        mac.init(new SecretKeySpec(StringCommonUtils.getContentBytesByUTF8(signKey), SIGN_TYPE));
        byte[] localSignature = mac.doFinal(StringCommonUtils.getContentBytesByUTF8(s1));
        return Base64.encodeBase64String(localSignature);
    }
}
