package com.mt.controller;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 13:59
 * {@code @project} BankSecuritySystem
 *
 */


import com.mt.common.biz.user.UserContext;
import com.mt.common.convention.result.Result;
import com.mt.common.convention.result.Results;
import com.mt.dao.entity.Account;
import com.mt.dto.req.AccountLoginIDReqDTO;
import com.mt.dto.req.AccountLoginReqDTO;
import com.mt.dto.req.AccountRegisterReqDTO;
import com.mt.dto.req.AccountSendCodeReqDTO;
import com.mt.dto.resp.AccountLoginRespDTO;
import com.mt.dto.resp.AccountSuccessLoginRespDTO;
import com.mt.service.IAccountService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.controller
 * {@code @className:}      AuthController
 * {@code @author:}         ma
 * {@code @date:}           2024/12/22 13:59
 * {@code @description:}    登录和注册功能实现
 */
@RestController
@RequiredArgsConstructor
public class AuthController {
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
    public Result<Void> SendEmail(@RequestBody AccountSendCodeReqDTO accountSendCodeReqDTO) {
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
    public Result<AccountSuccessLoginRespDTO> lastLogin(@RequestBody AccountLoginReqDTO accountLoginReqDTO,
                                                        ServletRequest request, ServletResponse response) {
        return Results.success(accountService.lastLogin(accountLoginReqDTO, request, response));
    }

    @GetMapping("/api/auth/test")
    public Result<Void> test(ServletRequest request, ServletResponse response) {

        Account account = UserContext.getAccount();
        if (account != null) {
            System.out.println("account = " + account);
        } else {
            throw new RuntimeException("用户未登录");
        }


        Result<Void> result = new Result<>();
        result.setCode("200");
        result.setMessage("dasdasdas");

        return result;
    }

    // 1.账号注册功能
    @PostMapping("/api/auth/register")
    public Result<Void> register(@RequestBody AccountRegisterReqDTO reqDTO) {
        accountService.register(reqDTO);
        return Results.success();
    }
}
