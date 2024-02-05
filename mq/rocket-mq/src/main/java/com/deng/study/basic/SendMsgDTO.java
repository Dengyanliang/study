package com.deng.study.basic;

import com.deng.study.enums.DelayTimeLevelEnum;
import lombok.Data;

/**
 * @Desc:
 * @Date: 2024/2/4 17:09
 * @Auther: dengyanliang
 */
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
