package com.mt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mt.dao.entity.LoginLog;
import com.mt.dao.mapper.LoginLogMapper;
import com.mt.service.ILoginLogService;
import org.springframework.stereotype.Service;

@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {
}
