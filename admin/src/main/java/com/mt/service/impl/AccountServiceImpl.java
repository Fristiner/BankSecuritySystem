package com.mt.service.impl;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 14:18
 * {@code @project} BankSecuritySystem
 *
 */


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mt.common.convention.errorcode.BaseErrorCode;
import com.mt.common.convention.result.Results;
import com.mt.dao.entity.Account;
import com.mt.dao.mapper.AccountMapper;
import com.mt.dto.req.AccountLoginIDReqDTO;
import com.mt.dto.req.AccountRegisterReqDTO;
import com.mt.dto.resp.AccountLoginIDRespDTO;
import com.mt.service.IAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.service.impl
 * {@code @className:}      AccountServiceImpl
 * {@code @author:}         ma
 * {@code @date:}           2024/12/22 14:18
 * {@code @description:}
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    /**
     * 账号注册功能
     * @param reqDTO
     * @return
     */
    @Override
    public Void register(AccountRegisterReqDTO reqDTO) {
        // 1.随机生成一个账号id
        String accountID = UUID.randomUUID().toString();
        Account account = new Account();
        account.setBalance(reqDTO.getBalance());
        account.setAccountId(accountID);
        account.setPassword(SecureUtil.sha256(reqDTO.getPassword()));
        account.setEmail(reqDTO.getEmail());

        return null;
    }










    /**
     * 登录验证 第一步
     * @param reqDTO
     * @return
     */
    @Override
    public AccountLoginIDRespDTO verifyLogin(AccountLoginIDReqDTO reqDTO) {
        //1.两次密码不相同
        if (reqDTO == null || !reqDTO.getPassword().equals(reqDTO.getConfirmPassword())) {
//            return Results.failure(400, "数据无效或两次密码不同");、
            System.out.println("asd");
        }
        // 2.两次密码相同
        // 去查找数据库
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id_number",reqDTO.getIdNumber())
                .eq("full_name",reqDTO.getFullName());

        Account account = getOne(queryWrapper);

        // 3. 检查查询结果及密码验证
        if (account == null || !account.getPassword().equals(reqDTO.getPassword())) {
//            return Results.failure(BaseErrorCode.SERVICE_ERROR, "身份验证失败");
        }
        // 4. 返回成功响应
        AccountLoginIDRespDTO respDTO = new AccountLoginIDRespDTO();
        BeanUtils.copyProperties(account, respDTO);
        // 注意：不要将敏感信息（如密码）包含在响应中
//        respDTO.setPassword(null); // 清除密码字段
//        String sha256 = SecureUtil.sha256(account.getPassword());
        return Results.success(respDTO).getData();
    }



}
