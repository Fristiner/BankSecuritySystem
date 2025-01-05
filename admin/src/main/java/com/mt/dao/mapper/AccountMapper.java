package com.mt.dao.mapper;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 14:11
 * {@code @project} BankSecuritySystem
 *
 */


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mt.dao.entity.Account;
import com.mt.dto.BalanceChangeHistoryDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.dao.mapper
 * {@code @className:}      AccountMapper
 * {@code @author:}         ma
 * {@code @date:}           2024/12/22 14:11
 * {@code @description:}
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    @Select("SELECT a.full_name, b.* " +
            "FROM account a " +
            "INNER JOIN balance_change_history b ON a.account_id = b.account_id " +
            "WHERE a.account_id = #{accountId} " +
            "ORDER BY b.change_time DESC limit 0,10")
    @Results({
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "balanceChangeId", column = "balance_change_id"),
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "changeAmount", column = "change_amount"),
            @Result(property = "newBalance", column = "new_balance"),
            @Result(property = "changeTime", column = "change_time"),
            @Result(property = "reason", column = "reason"),
            @Result(property = "type", column = "type")
    })
    List<BalanceChangeHistoryDTO> getBalanceChangeHistory(@Param("accountId") String accountId);
}
