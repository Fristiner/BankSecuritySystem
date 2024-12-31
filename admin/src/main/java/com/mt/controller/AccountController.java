package com.mt.controller;


import com.mt.common.convention.result.Result;
import com.mt.common.convention.result.Results;
import com.mt.dto.req.AccountRechargeReqDTO;
import com.mt.dto.req.AccountUpdateReqDTO;
import com.mt.service.IAccountService;
import lombok.RequiredArgsConstructor;
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

    // 充值功能
    @PostMapping("/api/account/recharge")
    public Result<Void> recharge(@RequestBody AccountRechargeReqDTO accountRechargeReqDTO) {
        accountService.recharge(accountRechargeReqDTO);
        return Results.success();
    }

    /**
     * 转账功能
     */

    public Result<Void> transfer() {

        return Results.success();
    }

    /**
     * 更新账户信息，可以修改家庭地址和手机号
     *
     * @param account
     * @return
     */
    @PostMapping("/api/account/update")
    public Result<Void> update(@RequestBody AccountUpdateReqDTO accountUpdateReqDTO) {
        return Results.success();
    }

    //deposit 存款接口


    // transfer 转账接口

    // withdraw 取款接口

    // balance 查询余额

    // transactions 查询交易历史

    //
}
