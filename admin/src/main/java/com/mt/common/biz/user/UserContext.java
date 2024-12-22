package com.mt.common.biz.user;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 14:49
 * {@code @project} BankSecuritySystem
 *
 */


import com.mt.dao.entity.Account;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.common.biz.user
 * {@code @className:}      UserContent
 * {@code @author:}         ma
 * {@code @date:}           2024/12/22 14:49
 * {@code @description:}    用户数据上下文
 */
public final class UserContext {
    private static final ThreadLocal<Account> ACCOUNT_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取用户上下文
     * @return
     */
    public static Account getAccount() {
        return ACCOUNT_THREAD_LOCAL.get();
    }
    public static void saveAccount(Account account) {
        ACCOUNT_THREAD_LOCAL.set(account);
    }

    public static void removeAccount() {
        ACCOUNT_THREAD_LOCAL.remove();
    }


}
