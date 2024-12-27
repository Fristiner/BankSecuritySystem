package com.mt.dao.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * login_log  登录日志表
 */
@Data
@TableName("login_log")
@Builder
public class LoginLog {

    @TableId(type = IdType.ASSIGN_UUID,value = "login_id")
    private String loginId;
    /**
     * 关联的账户ID
     */
    @TableField(value = "account_id")
    private String accountId; // 同样使用String类型

    /**
     * 用户IP地址，支持IPv4和IPv6
     */
    private String ipAddress;

    /**
     * 浏览器信息，例如：Chrome, Firefox 等
     */
    private String browser;

    /**
     * 操作系统信息，例如：Windows, macOS, Linux 等
     */
    private String os;

    /**
     * 访问设备信息，例如：Desktop, Mobile, Tablet 等
     */
    private String device;

    /**
     * 登录时间戳，默认为当前时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime loginTime;

    /**
     * 逻辑删除标志，默认为0表示未删除
     */
    @TableLogic
    private Integer delFlag;





}
