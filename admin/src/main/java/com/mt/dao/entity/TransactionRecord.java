package com.mt.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@TableName("transaction")
public class TransactionRecord {

    @TableId(type = IdType.ASSIGN_ID, value = "transaction_id") // 使用自定义的UUID作为主键
    private byte[] transactionId;

    private byte[] fromAccountId;

    private byte[] toAccountId;

    private BigDecimal amount;

    @TableField(value = "type")
    private String type; // 枚举类型在Java中通常用String表示

    private String status;

    private LocalDateTime transactionTime;

    private String description;

    @TableLogic
    private Integer deleted;

    public static byte[] generateTransactionId() {
        return UUID.randomUUID().toString().getBytes();
    }
}