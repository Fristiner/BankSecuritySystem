package com.mt.dto.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRechargeReqDTO {
    private BigDecimal amount;
}
