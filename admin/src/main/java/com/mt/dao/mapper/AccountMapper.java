package com.mt.dao.mapper;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 14:11
 * {@code @project} BankSecuritySystem
 *
 */


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mt.dao.entity.Account;
import org.apache.ibatis.annotations.Mapper;

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
}
