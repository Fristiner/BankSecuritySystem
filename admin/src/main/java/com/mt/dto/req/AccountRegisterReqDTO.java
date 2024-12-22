package com.mt.dto.req;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 15:34
 * {@code @project} BankSecuritySystem
 *
 */


import lombok.Data;

import java.math.BigDecimal;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.dto.req
 * {@code @className:}      AccountRegisterReqDTO
 * {@code @author:}         ma
 * {@code @date:}           2024/12/22 15:34
 * {@code @description:}   注册账号需要输入的数据
 */
@Data
public class AccountRegisterReqDTO {
    // 对于username
    private String name;
    //
    private String password;

    //
    private String email;

    private String phoneNumber;

    private String address;
    // 初始账号余额
    private BigDecimal balance;

}
