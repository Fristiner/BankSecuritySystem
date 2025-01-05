package com.mt.controller;

import com.mt.common.convention.result.Result;
import com.mt.common.convention.result.Results;
import com.mt.dto.req.TransactionLogReqDTO;
import com.mt.dto.resp.TransactionLogRespDTO;
import com.mt.service.IAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class transactionController {
    private final IAccountService accountService;

    // 查询转账历史信息
    @GetMapping("/api/transaction/logs")
    public Result<List<TransactionLogRespDTO>> transactionLog(HttpServletRequest request, HttpServletResponse response) {
        TransactionLogReqDTO transactionLogReqDTO = new TransactionLogReqDTO();
        // 模拟返回转账历史信息
        List<TransactionLogRespDTO> transactionLogRespDTOList = accountService.transactionLog(transactionLogReqDTO, request, response);
        return Results.success(transactionLogRespDTOList);
    }
}
