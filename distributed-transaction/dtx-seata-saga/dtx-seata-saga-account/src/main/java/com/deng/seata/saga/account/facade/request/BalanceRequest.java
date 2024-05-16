package com.deng.seata.saga.account.facade.request;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @Desc:
 * @Date: 2024/5/16 10:25
 * @Auther: dengyanliang
 */
@Data
public class BalanceRequest implements Serializable {
    private String orderNo;
    private int userId;

    private BalanceInfo[] balanceInfoArray;
    private List<BalanceInfo> balanceInfoList;
    private Map<String,BalanceInfo> balanceInfoMap;
}
