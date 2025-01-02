package com.mt.dto.resp;

import lombok.Data;

import java.math.BigDecimal;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.dto.resp
 * {@code @author:}         ma
 * {@code @date:}           2025-01-01 下午11:02
 * {@code @description:}
 */
@Data
public class AccountBalanceRespDTO {
    private BigDecimal balance;
}
