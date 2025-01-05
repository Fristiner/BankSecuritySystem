package com.mt;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/23 19:08
 * {@code @project} BankSecuritySystem
 *
 */


import cn.hutool.core.lang.UUID;
import com.mt.dao.entity.Account;
import com.mt.dao.mapper.AccountMapper;
import com.mt.dto.BalanceChangeHistoryDTO;
import com.mt.dto.resp.TransactionLogRespDTO;
import com.mt.utils.EmailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        PACKAGE_NAME
 * {@code @className:}      com.mt.test01
 * {@code @author:}         ma
 * {@code @date:}           2024/12/23 19:08
 * {@code @description:}
 */

@SpringBootTest
public class test01 {


    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void test01() {
        String string = UUID.randomUUID().toString(true);
        System.out.println(string);
        System.out.println(string.substring(0, 16));
        Account account = new Account();
        account.setAccountId(string.substring(0, 16));
        account.setPassword("123456");
        account.setEmail("asdasd@qq.com");
        account.setIdNumber("411528200210022989");
        account.setUsername("张三");
        account.setFullName("张三");
        account.setBalance(BigDecimal.ZERO);
        accountMapper.insert(account);
    }

    @Test
    public void test02() {
        List<BalanceChangeHistoryDTO> list = accountMapper.getBalanceChangeHistory("a3768f97503c434d");
        System.out.println(list);
        for (BalanceChangeHistoryDTO balanceChangeHistoryDTO : list) {
            System.out.println(balanceChangeHistoryDTO);
            System.out.println("姓名" + balanceChangeHistoryDTO.getFullName());
            System.out.println("reason" + balanceChangeHistoryDTO.getReason());

        }

    }

    @Test
    public void test03() {
        List<BalanceChangeHistoryDTO> list = accountMapper.getBalanceChangeHistory("a3768f97503c434d");

        List<TransactionLogRespDTO> transactionLogRespDTOList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (BalanceChangeHistoryDTO item : list) {
            TransactionLogRespDTO resp = new TransactionLogRespDTO();

            // 操作人姓名
            resp.setOperatorName(item.getFullName());

            // 操作时间，格式化为字符串
            LocalDateTime changeTime = item.getChangeTime().toInstant()
                    .atZone(ZoneId.of("UTC")) // 假设数据库时间是 UTC
                    .withZoneSameInstant(ZoneId.systemDefault()) // 转换为本地时区
                    .toLocalDateTime();
            resp.setOperatorTime(Timestamp.valueOf(changeTime.format(formatter)));

            // 交易金额，格式化为两位小数的字符串
            resp.setOperatorAmount(String.format("%.2f", item.getChangeAmount()));

            // 交易类型
            resp.setOperatorType(item.getType());

            // 交易后余额，格式化为两位小数的字符串
            resp.setOperatorChangedAmount(String.format("%.2f", item.getNewBalance()));

            // 备注，处理可能的空值
            resp.setOperatorRemark(item.getReason() != null ? item.getReason() : "");

            // 添加到结果列表
            transactionLogRespDTOList.add(resp);
        }

        // 打印结果
        for (TransactionLogRespDTO transactionLogRespDTO : transactionLogRespDTOList) {
            System.out.println(transactionLogRespDTO);
            System.out.println(transactionLogRespDTO.getOperatorTime());
        }

    }
}
