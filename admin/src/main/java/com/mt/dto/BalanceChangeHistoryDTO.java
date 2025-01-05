package com.mt.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class BalanceChangeHistoryDTO {
    @TableField("full_name")
    private String fullName;

    @TableField("balance_change_id")
    private String balanceChangeId;

    @TableField("account_id")
    private String accountId;

    @TableField("change_amount")
    private BigDecimal changeAmount;

    @TableField("new_balance")
    private BigDecimal newBalance;

    @TableField("change_time")
    private Timestamp changeTime;

    @TableField("reason")
    private String reason;

    @TableField("type")
    private String type;

    // getters and setters
}