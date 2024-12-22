package com.mt.dao.entity;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 14:05
 * {@code @project} BankSecuritySystem
 *
 */


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.dao.entity
 * {@code @className:}      Account
 * {@code @author:}         ma
 * {@code @date:}           2024/12/22 14:05
 * {@code @description:}
 */

@Data
@TableName("account")
public class Account {
    @TableId(type = IdType.ASSIGN_ID,value = "account_id")
    private String accountId;

    private BigDecimal balance;
    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private String fullName;

    private Date dateBirth;

    private String address;

    private String idNumber;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private String status;

    private String role;

    private LocalDateTime lastLogin;

    private Integer failedAttempts;

    @TableLogic
    private Integer deleted;
}
