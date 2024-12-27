package com.mt.dto.req;


import lombok.Data;

@Data
public class AccountSendCodeReqDTO {

    // 邮箱地址
    private String email;
    // 上一次生成的token值
    private String token;

}
