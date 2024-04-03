package com.deng.restroom.filter;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.deng.common.exception.BizException;
import com.deng.restroom.annotation.SignVerify;
import com.deng.restroom.config.VerifySignPreperties;
import com.deng.restroom.enums.RestRoomCodeEnum;
import com.deng.restroom.model.req.BaseReq;
import com.deng.restroom.utils.SHA256Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @Desc: 全局请求body数据签名验证
 * @Date: 2024/4/3 17:27
 * @Auther: dengyanliang
 */
@Slf4j
@ControllerAdvice
public class VerifySignRequestDataAdapter extends RequestBodyAdviceAdapter {

    @Resource
    private VerifySignPreperties verifySignPreperties;

    //时间戳简单正则匹配
    private static final Pattern TIMESTAMP_PATTERN = Pattern.compile("^\\d{13}$");

    /**
     * 对 存在SignVerify 注解的方法进行签名验证
     * @param methodParameter 方法参数
     * @param targetType 目标类
     * @param converterType 转换器
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(SignVerify.class);
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        try {
            BaseReq baseReq = (BaseReq) body;
            if (Objects.isNull(baseReq.getAppId())) {
                throw new BizException(RestRoomCodeEnum.SIGN_VALID_ERROR);
            }
            VerifySignPreperties.AppSignConfig appSignConfig = verifySignPreperties.getAppSignConfig(baseReq.getAppId());
            if (Objects.isNull(appSignConfig)) {
                throw new BizException(RestRoomCodeEnum.SIGN_VALID_ERROR);
            }
            // 是否启用签名验证，如果不启用，则直接放行
            if (!appSignConfig.getEnableSign()) {
                return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
            }
            // 验证请求时间是否合法
            String timestamp = verifyTimestamp(inputMessage, appSignConfig);

            // 验证请求参数中是否包含sign参数值
            String signValue = inputMessage.getHeaders().getFirst(appSignConfig.getSignHeaderName());
            if (StringUtils.isBlank(signValue)) {
                throw new BizException(RestRoomCodeEnum.SIGN_VALID_ERROR);
            }
            // 对body数据进行签名
            String decrypt = SHA256Util.getSign(baseReq.getAppId(), appSignConfig.getSignKey(), timestamp, JSON.toJSONString(body));
            // 比较签名数据是否一致
            if (!signValue.equals(decrypt)) {
                throw new BizException(RestRoomCodeEnum.SIGN_ILLEGAL);
            }

            return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
        } catch (BizException e){
            throw e;
        } catch (Exception e) {
            log.error("签名验证失败>>>>>>" + e.getMessage(), e);
            throw new BizException(RestRoomCodeEnum.SIGN_ILLEGAL);
        }
    }

    /**
     * 验证时间戳是否合法
     */
    @NotNull
    private String verifyTimestamp(HttpInputMessage inputMessage,VerifySignPreperties.AppSignConfig appSignConfig){
        // 获取时间戳（精确到毫秒）
        String timestamp = inputMessage.getHeaders().getFirst(appSignConfig.getSignTimeHeaderName());
        if(StringUtils.isBlank(timestamp)){ // 缺少timestamp参数
            throw new BizException(RestRoomCodeEnum.SIGN_VALID_ERROR);
        }

        if(!TIMESTAMP_PATTERN.matcher(timestamp).matches()){ // timestamp不是全数字或者长度错误
            throw new BizException(RestRoomCodeEnum.SIGN_VALID_ERROR);
        }

        DateTime requestTime = DateUtil.date(Long.parseLong(timestamp));
        Date now = DateUtil.date();
        // 这里是当前时间往后推5分钟
        DateTime nowAfterTime = DateUtil.offsetMillisecond(now, appSignConfig.getSystemTimeOffset());
        // 如果当前时间加5分钟后小于请求时间，则表示5分钟之后的请求，直接拒绝
        if(nowAfterTime.isBefore(requestTime)){
            throw new BizException(RestRoomCodeEnum.SYNC_TIME_ERROR);
        }

        // 这里是请求时间往后推5分钟
        DateTime requestAfterTime = DateUtil.offsetMillisecond(requestTime, appSignConfig.getSignEffectiveTime());
        // 如果请求时间加5分钟小于当前时间，则表明是5分钟之前的请求，直接拒绝
        if(requestAfterTime.isBefore(now)){
            throw new BizException(RestRoomCodeEnum.TIME_OUT_ERROR);
        }
        return timestamp;
    }

//    public static void main(String[] args) {
//        Date now = DateUtil.date();
//        // 请求方的时间不能超过本地系统的时间，允许有误差
//        DateTime dateTime = DateUtil.offsetMillisecond(now, 300000);
//        System.out.println(dateTime);
//    }
}
