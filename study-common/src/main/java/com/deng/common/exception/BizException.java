package com.deng.common.exception;

import com.deng.common.enums.BaseCodeEnum;
import com.deng.common.enums.CodeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Desc:
 * @Date: 2024/4/3 18:16
 * @Auther: dengyanliang
 */
@Getter
@Setter
public class BizException extends RuntimeException implements Serializable {

    private final CodeEnum codeEnum;


    /**
     * @param codeEnum 错误码
     * @param message 错误信息
     * @param cause 堆栈信息
     */
    public BizException(CodeEnum codeEnum, String message, Throwable cause) {
        super(message,cause);
        this.codeEnum = codeEnum;
    }

    public BizException(String message){
        super(message);
        codeEnum = BaseCodeEnum.FAIL;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        codeEnum = BaseCodeEnum.FAIL;
    }

    public BizException(Throwable cause) {
        super(cause);
        codeEnum = BaseCodeEnum.FAIL;
    }

    public BizException(CodeEnum codeEnum) {
        super(codeEnum.getMsg());
        this.codeEnum = codeEnum;
    }

    public BizException(CodeEnum codeEnum, String message) {
        super(message);
        this.codeEnum = codeEnum;
    }

    public String getCode(){
        return this.codeEnum.getCode();
    }
    public String getMsg(){
        return this.codeEnum.getMsg();
    }

}
