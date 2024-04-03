package com.deng.restroom.model.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 交易下单参数
 *
 * @author sunyb
 * @since 2024-01-25
 */
@Data
public class OrderNoReq extends AppIdReq {

    /**
     * 业务单号
     */
    @NotEmpty(message = "业务单号不能为空")
    private String orderNo;

}
