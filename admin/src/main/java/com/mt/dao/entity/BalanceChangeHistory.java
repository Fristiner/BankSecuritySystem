package com.mt.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("balance_change_history")
public class BalanceChangeHistory {
    @TableId(type = IdType.ASSIGN_ID, value = "balance_change_id")
    private String balanceChangeId;

    @TableField("account_id")
    private String accountId;

    @TableField("change_amount")
    private BigDecimal changeAmount;

    @TableField("new_balance")
    private BigDecimal newBalance;

    @TableField(value = "change_time", fill = FieldFill.INSERT)
    private LocalDateTime changeTime;

    @TableField("reason")
    private String reason;
    
    // 类型
    @TableField("type")
    private String type;
}
