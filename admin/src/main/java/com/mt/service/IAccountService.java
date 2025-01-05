package com.mt.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mt.dao.entity.Account;
import com.mt.dto.req.*;
import com.mt.dto.resp.AccountBalanceRespDTO;
import com.mt.dto.resp.AccountLoginRespDTO;
import com.mt.dto.resp.AccountSuccessLoginRespDTO;
import com.mt.dto.resp.TransactionLogRespDTO;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 14:14
 * {@code @project} BankSecuritySystem
 *
 */

public interface IAccountService extends IService<Account> {

    AccountLoginRespDTO verifyLogin(AccountLoginIDReqDTO reqDTO);

    void register(AccountRegisterReqDTO reqDTO);

    void SendEmail(AccountSendCodeReqDTO accountSendCodeReqDTO);

    AccountSuccessLoginRespDTO lastLogin(AccountLoginReqDTO accountLoginReqDTO, ServletRequest request, ServletResponse response);


    void deposit(AccountDepositReqDTO accountDepositReqDTO, HttpServletRequest request, HttpServletResponse response);


    void transfer(AccountTransferReqDTO accountTransferReqDTO, HttpServletRequest request, HttpServletResponse response);

    void withdraw(AccountWithdrawReqDTO accountWithdrawReqDTO, HttpServletRequest request, HttpServletResponse response);


    void changePassword(CardChangePasswordReqDTO cardChangePasswordReqDTO, HttpServletRequest request, HttpServletResponse response);


    AccountBalanceRespDTO getBalance(HttpServletRequest request, HttpServletResponse response);

    List<TransactionLogRespDTO> transactionLog(TransactionLogReqDTO transactionLogReqDTO, HttpServletRequest request, HttpServletResponse response);
}




