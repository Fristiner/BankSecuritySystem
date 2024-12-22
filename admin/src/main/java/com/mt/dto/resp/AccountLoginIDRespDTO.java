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
public class AccountLoginIDRespDTO {
    // 返回加密的邮箱系统
    private String encryEmail;
}
