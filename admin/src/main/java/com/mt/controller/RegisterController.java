package com.mt.controller;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 15:37
 * {@code @project} BankSecuritySystem
 *
 */


import com.mt.common.convention.result.Result;
import com.mt.dto.req.AccountRegisterReqDTO;
import com.mt.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.controller
 * {@code @className:}      RegisterController
 * {@code @author:}         ma
 * {@code @date:}           2024/12/22 15:37
 * {@code @description:}
 */
@RestController
@RequiredArgsConstructor
public class RegisterController {
    private final IAccountService accountService;

    // 1.账号注册功能
    @PostMapping("/api/auth/register")
    public Result<Void> register(@RequestBody AccountRegisterReqDTO reqDTO) {
        return accountService.register(reqDTO);
    }

    @GetMapping("/api/auth/getpost")
    public String TestPost() {
        return "success";
    }

}
