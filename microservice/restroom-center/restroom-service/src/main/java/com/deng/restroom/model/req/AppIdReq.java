package com.deng.restroom.model.req;

import com.deng.common.annotation.EnumCheck;
import com.deng.restroom.enums.AppIdEnum;
import lombok.Data;

@Data
public class AppIdReq implements BaseReq{
    /**
     * 应用id
     */
    @EnumCheck(enumClass = AppIdEnum.class,enumMethod = "checkAppId",message = "AppId传入有误")
    private String appId;


}
