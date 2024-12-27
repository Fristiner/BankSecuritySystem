package com.mt.controller;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 13:59
 * {@code @project} BankSecuritySystem
 *
 */


import com.mt.common.convention.result.Result;
import com.mt.common.convention.result.Results;
import com.mt.dto.req.AccountLoginIDReqDTO;
import com.mt.dto.req.AccountLoginReqDTO;
import com.mt.dto.req.AccountSendCodeReqDTO;
import com.mt.dto.resp.AccountLoginRespDTO;
import com.mt.dto.resp.AccountSuccessLoginRespDTO;
import com.mt.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.controller
 * {@code @className:}      LoginController
 * {@code @author:}         ma
 * {@code @date:}           2024/12/22 13:59
 * {@code @description:}
 */
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final IAccountService accountService;

    //1.登录接口1，成功返回一个200状态码，然后不返回数据，
    //      成功情况为：输入的账号和重复密码都正确 返回一个data 含有加盐后email地址和一个token，前端需要补齐邮箱地址才可以
    //
    @PostMapping("/api/auth/verifyLogin")
    public Result<AccountLoginRespDTO> verifyLogin(@RequestBody AccountLoginIDReqDTO reqDTO) {
        return Results.success(accountService.verifyLogin(reqDTO));
    }
    // 2.登录接口2 成功返回一个200状态码，然后不返回数据，
    //   传过来一个email 和 token  然后发送验证码到指定邮箱
    @PostMapping("/api/auth/sendCode")
    public Result<Void>  SendEmail(@RequestBody AccountSendCodeReqDTO accountSendCodeReqDTO) {
        accountService.SendEmail(accountSendCodeReqDTO);
        return Results.success();
    }

    // 登录接口2
    //  传入数据为完整的邮箱地址和token
    //  如果然后token数据放到那个redis里面和那个

    // 登录接口3
    // 校验模块 传入一个验证码如果正确就会返回一个登录的token 值
    //
    @PostMapping("/api/auth/Login")
    public Result<AccountSuccessLoginRespDTO> lastLogin(@RequestBody AccountLoginReqDTO accountLoginReqDTO){
        return Results.success(accountService.lastLogin(accountLoginReqDTO));
    }
}
