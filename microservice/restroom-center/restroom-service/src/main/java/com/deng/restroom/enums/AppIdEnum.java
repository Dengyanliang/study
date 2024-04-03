package com.deng.restroom.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @Desc: APPID
 * @Date: 2024/3/21 15:38
 * @Auther: dengyanliang
 */
@Getter
@AllArgsConstructor
public enum AppIdEnum {

    QIMAI("10000","企迈"),



    ;


    private String code;
    private String desc;


    public static boolean checkAppId(String code) {
        if (Objects.isNull(code)){
            return false;
        }
        for (AppIdEnum item : AppIdEnum.values()) {
            if (item.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
}
