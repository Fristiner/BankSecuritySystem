package com.mt.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mt.dao.entity.Account;
import com.mt.dto.req.AccountLoginIDReqDTO;
import com.mt.dto.req.AccountRegisterReqDTO;
import com.mt.dto.resp.AccountLoginIDRespDTO;
import org.springframework.stereotype.Service;

/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 14:14
 * {@code @project} BankSecuritySystem
 *
 */

public interface IAccountService extends IService<Account> {

    AccountLoginIDRespDTO verifyLogin(AccountLoginIDReqDTO reqDTO);

    Void register(AccountRegisterReqDTO reqDTO);
}
