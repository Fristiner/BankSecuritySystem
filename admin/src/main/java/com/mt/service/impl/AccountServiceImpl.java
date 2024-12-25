package com.mt.service.impl;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 14:18
 * {@code @project} BankSecuritySystem
 *
 */


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mt.common.convention.errorcode.BaseErrorCode;
import com.mt.common.convention.exception.ClientException;
import com.mt.dao.entity.Account;
import com.mt.dao.mapper.AccountMapper;
import com.mt.dto.req.AccountLoginIDReqDTO;
import com.mt.dto.req.AccountRegisterReqDTO;
import com.mt.dto.resp.AccountLoginRespDTO;
import com.mt.service.IAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.service.impl
 * {@code @className:}      AccountServiceImpl
 * {@code @author:}         ma
 * {@code @date:}           2024/12/22 14:18
 * {@code @description:}
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    /**
     * 账号注册功能
     *
     * @param reqDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(AccountRegisterReqDTO reqDTO) {
// 验证输入参数
        if (reqDTO == null) {
            throw new ClientException(BaseErrorCode.USER_NULL_ERROR);
        }
        QueryWrapper<Account> idQueryWrapper = new QueryWrapper<>();
        idQueryWrapper.eq("id_number", reqDTO.getIdNumber());
        if (this.count(idQueryWrapper) > 0) {
            throw new ClientException(BaseErrorCode.USER_ID_NUMBER_ERROR);
        }
        // 检查邮箱是否已注册
        QueryWrapper<Account> emailQueryWrapper = new QueryWrapper<>();
        emailQueryWrapper.eq("email", reqDTO.getEmail());
        if (this.count(emailQueryWrapper) > 0) {
            throw new ClientException(BaseErrorCode.USER_EMAIL_EXIST_ERROR);
        }
        // 检查身份证号码是否已注册
        try {
            // 创建新的账户实体
            Account account = new Account();
//        account.setAccountId(UUID.randomUUID().toString().replaceAll("-", ""));
            String accountId = UUID.randomUUID().toString().replaceAll("-", "");
            String accountIDNew = accountId.substring(0, 16);
            account.setAccountId(accountIDNew);
            account.setPassword(SecureUtil.sha256(reqDTO.getPassword()));
            account.setEmail(reqDTO.getEmail());
            account.setUsername(reqDTO.getName()); // 如果有用户名字段的话
            account.setFullName(reqDTO.getName());
            account.setPhoneNumber(reqDTO.getPhoneNumber());
            account.setIdNumber(reqDTO.getIdNumber());
            account.setBalance(reqDTO.getBalance());
//        account.setDateBirth(reqDTO.getDateBirth());
            account.setAddress(reqDTO.getAddress());

            // 设置默认状态和角色等信息
            account.setStatus("active"); // 或者其他默认状态
            account.setRole("user"); // 默认用户角色

            // 使用 MyBatis Plus 保存账户信息
            boolean saveSuccess = this.save(account);
            if (!saveSuccess) {
                throw new ClientException(BaseErrorCode.SERVICE_ERROR);
            }

        } catch (Exception e) {
//            log.error("注册过程出现异常，{}", e);
            throw new RuntimeException(e);
        }

    }


    /**
     * 登录验证 第一步
     *
     * @param reqDTO
     * @return
     */
    @Override
    public AccountLoginRespDTO verifyLogin(AccountLoginIDReqDTO reqDTO) {
        //1.两次密码不相同
        if (reqDTO == null || !reqDTO.getPassword().equals(reqDTO.getConfirmPassword())) {
//            return Results.failure(400, "数据无效或两次密码不同");、
            throw new ClientException(BaseErrorCode.USER_LOGIN_ERROR);
//            System.out.println("asd");
        }
        // 2.两次密码相同
        // 去查找数据库
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id_number", reqDTO.getIdNumber());

        Account account = getOne(queryWrapper);

        // 用户没有注册
        if (account == null) {
            throw new ClientException(BaseErrorCode.USER_NO_EXIST_ERROR);
        }

        // 3. 检查查询结果及密码验证
        if (!account.getPassword().equals(SecureUtil.sha256(reqDTO.getPassword()))) {
            throw new ClientException(BaseErrorCode.USER_PASSWORD_ERROR);
        }

        // 4. 返回成功响应
        AccountLoginRespDTO respDTO = new AccountLoginRespDTO();
        respDTO.setEmail(account.getEmail());
        // TODO： 需要引入一个token 和redis对应的关系，然后确保下一次获取验证码时候找到对应的对象


        // 注意：不要将敏感信息（如密码）包含在响应中
//        respDTO.setPassword(null); // 清除密码字段
//        String sha256 = SecureUtil.sha256(account.getPassword());
        return respDTO;
    }


}
