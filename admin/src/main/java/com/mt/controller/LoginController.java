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
import com.mt.dto.resp.AccountLoginIDRespDTO;
import com.mt.service.IAccountService;
import jakarta.servlet.http.HttpServletRequest;
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

    //1.登录接口，返回一个200状态码，然后不返回数据，
    @PostMapping("/api/auth/verifyLogin")
    public Result<AccountLoginIDRespDTO> verifyLogin(@RequestBody AccountLoginIDReqDTO reqDTO) {
        return Results.success(accountService.verifyLogin(reqDTO));
    }
}
