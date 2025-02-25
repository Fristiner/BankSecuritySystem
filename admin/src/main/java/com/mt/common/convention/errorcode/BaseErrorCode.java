package com.mt.common.convention.errorcode;

public enum BaseErrorCode implements IErrorCode {

    // ========== 一级宏观错误码 客户端错误 ==========
    CLIENT_ERROR("A000001", "用户端错误"),

    // ========== 二级宏观错误码 用户注册错误 ==========
    USER_NULL_ERROR("A0001001", "传入数据为空"),
    USER_ID_NUMBER_ERROR("A0001002", "身份证号码已被注册"),
    USER_EMAIL_EXIST_ERROR("A0001003", "该邮箱已经被注册"),
    USER_LOGIN_ERROR("A0001004", "数据无效，无请求数据"),
    USER_NO_EXIST_ERROR("A0001005", "用户没有注册不存在"),
    USER_PASSWORD_ERROR("A0001006", "密码错误"),
    // ========== 一级宏观错误码 系统执行出错 ==========
    SERVICE_ERROR("B000001", "系统执行出错"),
    // ========== 二级宏观错误码 系统执行超时 ==========
    SERVICE_TIMEOUT_ERROR("B000100", "系统执行超时"),
    SAVE_OBJECT_ERROR("B0001001", "保存信息时出现错误，系统执行异常"),
    REGISTER_OTHER_ERROR("B0001002", "注册时出现其它错误，系统执行异常"),
    REDIS_GENERATE_ERROR("B0001003", "生成redis key时出现错误，系统执行异常"),
    EMAIL_SEND_ERROR("B0001004", "发送邮件时出现错误，系统执行异常"),
    TRANSFER_ACCOUNT_NOT_EXIST_ERROR("B0001005", "交易目标账户不存在"),
    // ========== 一级宏观错误码 调用第三方服务出错 ==========
    INSUFFICIENT_BALANCE_ERROR("C001001", "余额不足"),
    REMOTE_ERROR("C000001", "调用第三方服务出错"),
    NUMBER_SMALL_ERROR("403", "数字过小"),
    NUMBER_OVER_ERROR("401", "数字过大"),
    TOKEN_OVER_TIME_ERROR("C000003", "token已过期"),
    TOKEN_ERROR("C000002", "传入验证码不匹配或没有传入验证码值"),
    MISS_LOGIN_DATA("B0001005", "没有传入登录凭证数据");

    private final String code;

    private final String message;

    BaseErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
