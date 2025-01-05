create database bank;

use bank;
-- 创建账户表，存储系统账户的详细信息
CREATE TABLE account
(
    -- 账户唯一标识符，默认生成随机UUID并转换为二进制格式
    account_id      varchar(20) PRIMARY KEY,
    balance         DECIMAL(15, 2)                                     NOT NULL,
    -- 用户名，用于登录和其他识别目的，必须唯一且非空
    username        VARCHAR(50)                                        NOT NULL COMMENT '用户名，用于登录和其他识别目的，必须唯一且非空',

    -- 存储用户的密码哈希值（而非明文），非空
    password        VARCHAR(255)                                       NOT NULL COMMENT '存储用户的密码哈希值（而非明文），非空',

    -- 用户电子邮件地址，通常作为次要登录凭证或找回密码的途径，可设置为唯一
    email           VARCHAR(100) UNIQUE COMMENT '用户电子邮件地址，通常作为次要登录凭证或找回密码的途径，可设置为唯一',

    -- 用户电话号码，可用于验证、通知等功能
    phone_number    VARCHAR(20) COMMENT '用户电话号码，可用于验证、通知等功能',

    -- 用户全名，用于显示和法律文档等，非空
    full_name       VARCHAR(100)                                       NOT NULL COMMENT '用户全名，用于显示和法律文档等，非空',

    -- 用户出生日期，用于年龄验证和KYC（了解你的客户）流程
    date_birth      DATE COMMENT '用户出生日期，用于年龄验证和KYC（了解你的客户）流程',

    -- 用户居住地址信息
    address         TEXT COMMENT '用户居住地址信息',

    -- 用户身份证号码，18位，不可重复，非空
    id_number       CHAR(18) UNIQUE                                    NOT NULL COMMENT '用户身份证号码，18位，不可重复，非空',

    -- 账户创建时间戳，默认为当前时间，非空
    create_time     TIMESTAMP                                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '账户创建时间戳，默认为当前时间，非空',

    -- 最后一次更新账户信息的时间戳，默认为当前时间，每当记录被修改时自动更新
    update_time     TIMESTAMP                                          NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新账户信息的时间戳，默认为当前时间，每当记录被修改时自动更新',

    -- 账户状态，例如：active, pending, suspended, deleted 等，非空，默认为 'pending'
    status          ENUM ('active', 'pending', 'suspended', 'deleted') NOT NULL DEFAULT 'pending' COMMENT '账户状态，例如：active, pending, suspended, deleted 等，非空，默认为 ''pending''',

    -- 用户角色，比如普通用户、管理员等，非空，默认为普通用户
    role            ENUM ('user', 'admin')                             NOT NULL DEFAULT 'user' COMMENT '用户角色，比如普通用户、管理员等，非空，默认为普通用户',

    -- 上次登录的时间戳，用于安全监控和统计分析
    last_login      TIMESTAMP COMMENT '上次登录的时间戳，用于安全监控和统计分析',

    -- 登录失败尝试次数，用于防止暴力破解攻击，默认为 0
    failed_attempts SMALLINT UNSIGNED                                  NOT NULL DEFAULT 0 COMMENT '登录失败尝试次数，用于防止暴力破解攻击，默认为 0',

    -- 逻辑删除标志，默认为0表示未删除
    deleted         TINYINT(1)                                         NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，默认为0表示未删除'
);

-- 确保身份证号码长度为18位
ALTER TABLE account
    ADD CONSTRAINT check_id_number CHECK (LENGTH(id_number) = 18);