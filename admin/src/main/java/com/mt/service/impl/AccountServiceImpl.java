package com.mt.service.impl;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 14:18
 * {@code @project} BankSecuritySystem
 *
 */


import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mt.common.constant.RedisConstant;
import com.mt.common.convention.errorcode.BaseErrorCode;
import com.mt.common.convention.exception.ClientException;
import com.mt.dao.entity.Account;
import com.mt.dao.mapper.AccountMapper;
import com.mt.dto.req.AccountLoginIDReqDTO;
import com.mt.dto.req.AccountLoginReqDTO;
import com.mt.dto.req.AccountRegisterReqDTO;
import com.mt.dto.req.AccountSendCodeReqDTO;
import com.mt.dto.resp.AccountLoginRespDTO;
import com.mt.dto.resp.AccountSuccessLoginRespDTO;
import com.mt.service.IAccountService;
import com.mt.utils.EmailUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
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
                throw new ClientException(BaseErrorCode.SERVICE_ERROR);
            }

        } catch (Exception e) {
//            log.error("注册过程出现异常，{}", e);
            throw new RuntimeException(e);
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
                throw new ClientException(BaseErrorCode.SERVICE_ERROR);
            }
            // 如果Token匹配，则可以认为用户是合法的，可以选择直接返回而不重新发送验证码
        } else {
            // 如果没有数据，或者数据为空，继续执行发送验证码的逻辑
            throw new ClientException(BaseErrorCode.SERVICE_ERROR);
        }
        // 此时已经校验成功
        // 生成随机的验证码
        // 发送验证码到邮箱
        try {
            String randomNumbers = RandomUtil.randomNumbers(6);
            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(
                    RedisConstant.SEND_CODE_PREFIX + email, randomNumbers, 3, TimeUnit.MINUTES
            );
            if (result == null || !result){
            // TODO：抛出异常需要优化
                throw new ClientException(BaseErrorCode.SERVICE_ERROR);
            }

            // 发送邮箱
            Boolean sendCodeEmailResult = emailUtil.sendCodeToEmail(email, randomNumbers);
            if(!sendCodeEmailResult){
                // 发送失败，清理可能存在的Redis记录
                cleanupOnError(redisKey);
                throw new ClientException(BaseErrorCode.SERVICE_ERROR);
            }
//            log.("验证码已发送到邮箱：{},并已经存入redis中", email);
        } catch (Exception e) {
            log.error("发送验证码到邮箱或存入redis时发生异常，{}", e);
            throw new ClientException(BaseErrorCode.SERVICE_ERROR);
        }

    }

    /**
     * 最终登录校验
     * @param accountLoginReqDTO
     * @return
     */

    // 需要这两个信息 (ServletRequest request, ServletResponse response)
    @Override
    public AccountSuccessLoginRespDTO lastLogin(AccountLoginReqDTO accountLoginReqDTO) {
        //TODO： 完成最终的登录校验，将登录成功的用户信息存入redis中
        //   并且将用户的用户id关联信息存入UserContext中




        return null;
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
        // 生成一个随机的token
        String token = UUID.randomUUID().toString();
        // 将token与用户ID关联存储到Redis中
        Boolean setIfAbsent = stringRedisTemplate.opsForValue().setIfAbsent(
                RedisConstant.EMAIL_PREFIX+account.getEmail(), token, 5, TimeUnit.MINUTES);
        if (Boolean.FALSE.equals(setIfAbsent)) {
            throw new ClientException(BaseErrorCode.SERVICE_ERROR);
        }
        respDTO.setToken(token);
        // 注意：不要将敏感信息（如密码）包含在响应中
//        respDTO.setPassword(null); // 清除密码字段
//        String sha256 = SecureUtil.sha256(account.getPassword());
        return respDTO;
    }


}
