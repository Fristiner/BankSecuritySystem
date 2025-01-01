package com.mt.controller;


import com.mt.common.convention.result.Result;
import com.mt.common.convention.result.Results;
import com.mt.dto.req.AccountBalanceReqDTO;
import com.mt.dto.req.AccountDepositReqDTO;
import com.mt.dto.req.AccountTransferReqDTO;
import com.mt.dto.req.AccountWithdrawReqDTO;
import com.mt.dto.resp.AccountBalanceRespDTO;
import com.mt.service.IAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 账户相关接口，如查询余额、查询交易历史、修改密码。
 */
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final IAccountService accountService;

    // 充值功能  deposit 存款接口

    @GetMapping("/api/account/deposit")
    public Result<Void> deposit(@RequestBody AccountDepositReqDTO accountDepositReqDTO, HttpServletRequest request, HttpServletResponse response) {

        System.out.println(request.getHeader("Authorization"));

        System.out.println(request.getHeader("Content-Type"));
//        accountService.deposit();
        // 打印请求对象的字符串表示
        System.out.println(request);
        accountService.deposit(accountDepositReqDTO, request, response);
        return Results.success();
    }


    // transfer 转账接口
    @PostMapping("/api/account/transfer")
    public Result<Void> transfer(@RequestBody AccountTransferReqDTO accountTransferReqDTO, HttpServletRequest request, HttpServletResponse response) {

        accountService.transfer(accountTransferReqDTO, request, response);
        return Results.success();
    }

    // withdraw 取款接口
    @PostMapping("/api/account/withdraw")
    public Result<Void> withdraw(@RequestBody AccountWithdrawReqDTO accountWithdrawReqDTO, HttpServletRequest request, HttpServletResponse response) {
        accountService.withdraw(accountWithdrawReqDTO, request, response);
        return Results.success();
    }


    //  balance 查询余额
    @GetMapping("/api/account/balance")
    public Result<AccountBalanceRespDTO> getBalance(@RequestBody AccountBalanceReqDTO accountBalanceReqDTO, HttpServletRequest request, HttpServletResponse response) {

        // 模拟返回余额
        return Results.success(accountService.getBalance(accountBalanceReqDTO, request, response));
    }

//    // transactions 查询交易历史
//    @GetMapping("/api/account/transactions")
//    public Result<Void> getTransactions() {
//
//        return Results.success();
//    }

}
