package com.deng.study.io.netty;

import lombok.Data;
import lombok.ToString;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/9 17:43
 */
@Data
@ToString(callSuper = true)
public class LoginRequestMessage extends MyMessage{

    private String userName;
    private String password;
    private String nickName;

    public LoginRequestMessage(String userName, String password, String nickName) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
    }

    @Override
    public int getMessageType() {
        return 0;
    }
}
