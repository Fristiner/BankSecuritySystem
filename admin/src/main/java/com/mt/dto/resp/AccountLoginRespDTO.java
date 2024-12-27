package com.mt.dto.resp;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 15:16
 * {@code @project} BankSecuritySystem
 *
 */


import lombok.Data;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.dto.resp
 * {@code @className:}      AccountLoginIDRespDTO
 * {@code @author:}         ma
 * {@code @date:}           2024/12/22 15:16
 * {@code @description:}
 */
@Data
public class AccountLoginRespDTO {
    // 返回邮箱系统
    private String email;

    // token 用来进行校验
    private String token;

}
