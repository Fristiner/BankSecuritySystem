package com.mt.controller;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 15:37
 * {@code @project} BankSecuritySystem
 *
 */


import com.mt.common.convention.errorcode.BaseErrorCode;
import com.mt.common.convention.exception.ClientException;
import com.mt.common.convention.result.Result;
import com.mt.common.convention.result.Results;
import com.mt.dto.req.AccountRegisterReqDTO;
import com.mt.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        accountService.register(reqDTO);
        return Results.success();
    }

    @GetMapping("/api/auth/getpost")
    public String TestPost() {
        return "success";
    }

    @GetMapping("/api/auth/test/{test}")
    public Result<Void> test(@PathVariable("test") Integer test) {
        if (test > 0) {
            throw new ClientException(BaseErrorCode.NUMBER_OVER_ERROR);
        }
        if (test < 0) {
            throw new ClientException(BaseErrorCode.NUMBER_SMALL_ERROR);
        }

        return Results.success();

    }


}
