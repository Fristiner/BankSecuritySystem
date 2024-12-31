package com.mt.controller;


import com.mt.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final IAccountService accountService;
}
