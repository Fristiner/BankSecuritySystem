package com.mt.controller;

import com.mt.common.convention.result.Result;
import com.mt.common.convention.result.Results;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class transactionController {


    //TODO：转账功能
    //  1.转帐目标账号


    public Result<Void> Transaction() {

        return Results.success();
    }
}
