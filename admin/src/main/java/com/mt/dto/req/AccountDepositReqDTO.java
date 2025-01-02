package com.mt.dto.req;

import lombok.Data;

import java.math.BigDecimal;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.dto.req
 * {@code @author:}         ma
 * {@code @date:}           2025-01-01 下午10:58
 * {@code @description:}
 */
@Data
public class AccountDepositReqDTO {
    // 充值金额
    private BigDecimal amount;

}
