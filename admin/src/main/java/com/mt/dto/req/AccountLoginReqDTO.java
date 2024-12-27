package com.mt.dto.req;


import lombok.Data;

@Data
public class AccountLoginReqDTO {
    // 邮箱地址
    private String email;
    // 提交的验证码
    private String code;
}
