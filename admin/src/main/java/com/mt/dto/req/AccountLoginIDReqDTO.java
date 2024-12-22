package com.mt.dto.req;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 15:06
 * {@code @project} BankSecuritySystem
 *
 */


import lombok.Data;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.dto.req
 * {@code @className:}      AccountLogin
 * {@code @author:}         ma
 * {@code @date:}           2024/12/22 15:06
 * {@code @description:}
 */
@Data
public class AccountLoginIDReqDTO {
    // 身份证号码
    private String idNumber;
    // 全名
    private String fullName;
    // 密码
    private String password;
    // 确实密码
    private String confirmPassword;
}
