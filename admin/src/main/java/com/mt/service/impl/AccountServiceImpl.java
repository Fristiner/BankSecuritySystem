package com.mt.service.impl;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 14:18
 * {@code @project} BankSecuritySystem
 *
 */


import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mt.common.biz.user.UserContext;
import com.mt.common.constant.RedisConstant;
import com.mt.common.convention.errorcode.BaseErrorCode;
import com.mt.common.convention.exception.ClientException;
import com.mt.dao.entity.Account;
import com.mt.dao.entity.LoginLog;
import com.mt.dao.mapper.AccountMapper;
import com.mt.dao.mapper.LoginLogMapper;
import com.mt.dto.req.*;
import com.mt.dto.resp.AccountLoginRespDTO;
import com.mt.dto.resp.AccountSuccessLoginRespDTO;
import com.mt.service.IAccountService;
import com.mt.utils.EmailUtil;
import com.mt.utils.LinkUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

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


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private EmailUtil emailUtil;
    @Resource
    private AccountMapper accountMapper;

    @Resource
    private LoginLogMapper loginLogMapper;

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

            // 设置账户信息
//        account.setAccountId(UUID.randomUUID().toString().replaceAll("-", ""));
            String ID = cn.hutool.core.lang.UUID.randomUUID().toString(true);
            String accountIDNew = ID.substring(0, 16);
//            String accountId = UUID.randomUUID().toString().replaceAll("-", "");
//            String accountIDNew = accountId.substring(0, 16);
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
                throw new ClientException(BaseErrorCode.SAVE_OBJECT_ERROR);
            }
        } catch (Exception e) {
//            log.error("注册过程出现异常，{}", e);
            throw new ClientException(BaseErrorCode.REGISTER_OTHER_ERROR);
        }

    }


    // 发送邮箱
    @Override
    public void SendEmail(AccountSendCodeReqDTO accountSendCodeReqDTO) {
        String email = accountSendCodeReqDTO.getEmail();
        String previousToken = accountSendCodeReqDTO.getToken();
// 1. 在Redis中查询上一次存储的信息
        String redisKey = RedisConstant.EMAIL_PREFIX + email;
        String existingToken = stringRedisTemplate.opsForValue().get(redisKey);
        if (existingToken != null && !existingToken.isEmpty()) {
            // 如果有数据，进行校验
            if (previousToken == null || !previousToken.equals(existingToken)) {
//                throw new IllegalArgumentException("上次生成的token值不匹配");

                throw new ClientException(BaseErrorCode.TOKEN_ERROR);
            }
            // 如果Token匹配，则可以认为用户是合法的，可以选择直接返回而不重新发送验证码
        } else {
            // 如果没有数据，或者数据为空，继续执行发送验证码的逻辑
            throw new ClientException(BaseErrorCode.TOKEN_OVER_TIME_ERROR);
        }
        // 此时已经校验成功
        // 生成随机的验证码
        // 发送验证码到邮箱
        try {
            String randomNumbers = RandomUtil.randomNumbers(6);
            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(
                    RedisConstant.SEND_CODE_PREFIX + email, randomNumbers, 3, TimeUnit.MINUTES
            );
            if (result == null || !result) {
                // TODO：抛出异常需要优化
                throw new ClientException(BaseErrorCode.REDIS_GENERATE_ERROR);
            }

            // 发送邮箱
            Boolean sendCodeEmailResult = emailUtil.sendCodeToEmail(email, randomNumbers);
            if (!sendCodeEmailResult) {
                // 发送失败，清理可能存在的Redis记录
                cleanupOnError(redisKey);
                throw new ClientException(BaseErrorCode.EMAIL_SEND_ERROR);
            }
//            log.("验证码已发送到邮箱：{},并已经存入redis中", email);
        } catch (Exception e) {
            log.error("发送验证码到邮箱或存入redis时发生异常，{}", e);
            throw new ClientException(BaseErrorCode.SERVICE_ERROR);
        }

    }

    //TODO：实现最后的登录操作

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AccountSuccessLoginRespDTO lastLogin(AccountLoginReqDTO accountLoginReqDTO, ServletRequest request, ServletResponse response) {
        // 接收发送过来的验证码，返回一个登录的token 并将用户信息存储到UserContent中
//        return null;
        // 1.获取返回的token
        String code = accountLoginReqDTO.getCode();
        String email = accountLoginReqDTO.getEmail();

        // 2.去redis中查询验证码
        String redisKey = RedisConstant.SEND_CODE_PREFIX + email;
        String redisCode = stringRedisTemplate.opsForValue().get(redisKey);
        if (redisCode == null || !redisCode.equals(code)) {
            // 抛出异常，没有这个数据登录失败
            throw new ClientException(BaseErrorCode.TOKEN_ERROR);
        }
        // 登录成功，删除redis 当中的验证码
        // 3.删除redis中的验证码
//        stringRedisTemplate.delete(redisKey);
        // 设置过期时间为50s
        // 如果过期时间超过50s 则设计过期时间为50s
//        stringRedisTemplate.expire(redisKey, 50, TimeUnit.SECONDS);
        Long ttl = stringRedisTemplate.getExpire(redisKey);
        if (ttl != null && ttl > 40) {
            stringRedisTemplate.expire(redisKey, 40, TimeUnit.SECONDS);
        }

        // 添加登录日志服务
        // 去数据库中查询用户信息
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("email", email);

        Account account = accountMapper.selectOne(accountQueryWrapper);

        if (account == null) {
            throw new ClientException(BaseErrorCode.USER_NO_EXIST_ERROR);
        }
        // 生成登陆的Authorization返回

        String authorization = cn.hutool.core.lang.UUID.randomUUID().toString(true);

        // 将信息存入到redis当中
//        BeanUtil.copyProperties(account, );

        String actualIp = LinkUtil.getActualIp(((HttpServletRequest) request));
        String browser = LinkUtil.getBrowser(((HttpServletRequest) request));
        String os = LinkUtil.getOs(((HttpServletRequest) request));
        String device = LinkUtil.getDevice(((HttpServletRequest) request));
        String network = LinkUtil.getNetwork(((HttpServletRequest) request));
        LoginLog loginLog = new LoginLog();

        loginLog.setAccountId(account.getAccountId());
        loginLog.setIpAddress(actualIp);
        loginLog.setBrowser(browser);
        loginLog.setOs(os);
        loginLog.setDevice(device);
//        loginLog.set(network);
        String login_id = cn.hutool.core.lang.UUID.randomUUID().toString(true).substring(0, 16);
        loginLog.setLoginId(login_id);

        int result = loginLogMapper.insert(loginLog);
        if (result != 1) {

            throw new ClientException(BaseErrorCode.SAVE_OBJECT_ERROR);
        }
        // TODO 保存authorization 到redis中去
        stringRedisTemplate.opsForValue().set(
                RedisConstant.AUTHORIZATION_PREFIX + authorization
                , account.getAccountId(), 30, TimeUnit.MINUTES);

        // 将用户数据放到上下文当中 确保前面登录不会出现问题后，才可以放入到登录上下文当中去
        UserContext.saveAccount(account);
        // 5.返回登录成功的信息
        AccountSuccessLoginRespDTO respDTO = new AccountSuccessLoginRespDTO();
        respDTO.setAuthorization(authorization);
        return respDTO;


        // 4.返回登录成功的信息
    }

    /**
     * 充值功能
     *
     * @param accountRechargeReqDTO
     */
    @Override
    public void recharge(AccountRechargeReqDTO accountRechargeReqDTO) {
        // TODO： 实现充值功能

    }

    private void LoginNetWork(ServletRequest request, ServletResponse response) {

    }

    private void cleanupOnError(String redisKey) {
        try {
            // 尝试删除可能存在的Redis记录
            stringRedisTemplate.delete(redisKey);
            log.warn("因错误清理了部分完成的状态：{}");
        } catch (Exception e) {
            log.error("清理部分完成状态时发生错误：{}");
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
        if (reqDTO == null) {
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
        // 生成一个随机的token
        String token = UUID.randomUUID().toString(true);
        // 将token与用户ID关联存储到Redis中
//        Boolean setIfAbsent = stringRedisTemplate.opsForValue().setIfAbsent(
//                RedisConstant.EMAIL_PREFIX + account.getEmail(), token, 5, TimeUnit.MINUTES);


        stringRedisTemplate.opsForValue().set(
                RedisConstant.EMAIL_PREFIX + account.getEmail(),
                token,
                5,
                TimeUnit.MINUTES
        );

//        if (Boolean.FALSE.equals(setIfAbsent)) {
//            throw new ClientException(BaseErrorCode.SERVICE_ERROR);
//        }

        respDTO.setToken(token);
        // 注意：不要将敏感信息（如密码）包含在响应中
//        respDTO.setPassword(null); // 清除密码字段
//        String sha256 = SecureUtil.sha256(account.getPassword());
        return respDTO;
    }


}
