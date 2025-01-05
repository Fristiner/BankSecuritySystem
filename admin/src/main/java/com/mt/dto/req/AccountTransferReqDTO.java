package com.mt.dto.req;

import lombok.Data;

import java.math.BigDecimal;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.dto.req
 * {@code @author:}         ma
 * {@code @date:}           2025-01-01 下午11:00
 * {@code @description:}
 */
@Data
public class AccountTransferReqDTO {
    // 目标身份证号码
    private String targetIdNumber;
    // 转账金额
    private BigDecimal amount;
    // 备注
    private String remark;
}
