package com.deng.study.dto;

import com.deng.study.enums.DelayTimeLevelEnum;
import lombok.Data;
import lombok.ToString;

/**
 * @Desc:
 * @Date: 2024/2/4 17:09
 * @Auther: dengyanliang
 */
@ToString
@Data
public class SendMsgDTO {
    private String payNo;
    private int retryTime;
    private DelayTimeLevelEnum delayTimeLevel;

    public SendMsgDTO(String payNo, int retryTime) {
        this.payNo = payNo;
        this.retryTime = retryTime;
    }

    public SendMsgDTO(String payNo, int retryTime, DelayTimeLevelEnum delayTimeLevel) {
        this.payNo = payNo;
        this.retryTime = retryTime;
        this.delayTimeLevel = delayTimeLevel;
    }
}
