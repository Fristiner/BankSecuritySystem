package com.mt.controller;

import com.mt.common.convention.result.Result;
import com.mt.common.convention.result.Results;
import com.mt.dto.req.TransactionLogReqDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class transactionController {

    //balance 查询余额

    //    @GetMapping("/api/account/balance")
//    public Result<Void> balance() {
//        // 模拟返回余额
//        return Results.success();
//    }


    // 查询转账历史信息
    @GetMapping("/api/transaction/logs")
    public Result<Void> transactionLog(@RequestBody TransactionLogReqDTO transactionLogReqDTO, HttpServletRequest request, HttpServletResponse response) {
        return Results.success();
    }
}
