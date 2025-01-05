package com.mt.dto.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.sql.Timestamp;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.dto.resp
 * {@code @author:}         ma
 * {@code @date:}           2025-01-05 上午11:36
 * {@code @description:}
 */
@Data
public class TransactionLogRespDTO {
    // 1.操作人姓名
    private String operatorName;
    //2.操作时间
    @TableField("change_time")
    private Timestamp operatorTime;
    //3.交易金额
    private String operatorAmount;
    //4.交易类型
    private String operatorType;
    //5.交易后余额
    private String operatorChangedAmount;
    //6.备注
    private String operatorRemark;
}
