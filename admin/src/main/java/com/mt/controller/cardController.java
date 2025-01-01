package com.mt.controller;

import com.mt.common.convention.result.Result;
import com.mt.common.convention.result.Results;
import com.mt.dto.req.CardChangePasswordReqDTO;
import com.mt.service.IAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.controller
 * {@code @className:}
 * {@code @author:}         ma
 * {@code @date:}           2024/12/31 下午9:34
 * {@code @description:}
 */

@RestController
@RequiredArgsConstructor
public class cardController {

    private final IAccountService accountService;

    // 修改密码
    @PostMapping("/api/card/changePassword")
    public Result<Void> changePassword(@RequestBody CardChangePasswordReqDTO cardChangePasswordReqDTO,
                                       HttpServletRequest request, HttpServletResponse response) {
        accountService.changePassword(cardChangePasswordReqDTO, request, response);
        return Results.success();
    }

    // 紧急挂失卡片，进入block状态
    @PostMapping("/api/card/block")
    public Result<Void> block() {
        return Results.success();
    }


}
