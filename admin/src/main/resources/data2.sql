CREATE TABLE transaction (
    -- 交易唯一标识符，默认生成随机UUID并转换为二进制格式
                             transaction_id varchar(20) PRIMARY KEY ,

    -- 发起交易的账户ID
                             from_account_id varchar(20),

    -- 接收交易的账户ID
                             to_account_id varchar(20),

    -- 交易金额
                             amount DECIMAL(15, 2) NOT NULL,

    -- 交易类型，例如：deposit, withdraw, transfer 等
                             type ENUM('deposit', 'withdraw', 'transfer') NOT NULL,

    -- 交易状态，例如：pending, completed, failed 等
                             status ENUM('pending', 'completed', 'failed') NOT NULL DEFAULT 'pending',

    -- 交易时间戳，默认为当前时间
                             transaction_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- 备注或交易描述
                             description TEXT,

    -- 逻辑删除标志，默认为0表示未删除
                             deleted TINYINT(1) NOT NULL DEFAULT 0
);

-- 余额变更明细表

CREATE TABLE balance_change_history (
    -- 变更记录唯一标识符，默认生成随机UUID并转换为二进制格式
                                        balance_change_id varchar(20) PRIMARY KEY ,

    -- 关联的账户ID
                                        account_id varchar(20) NOT NULL,

    -- 变更金额
                                        change_amount DECIMAL(15, 2) NOT NULL,

    -- 变更后的余额
                                        new_balance DECIMAL(15, 2) NOT NULL,

    -- 变更时间戳，默认为当前时间
                                        change_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- 变更原因（关联到具体的交易或操作）
                                        reason TEXT,
                                        type enum('payment', 'withdrawl','deposit','refund', 'transfer') NOT NULL,
);

-- 审计日志表

CREATE TABLE audit_log (
    -- 审计日志唯一标识符，默认生成随机UUID并转换为二进制格式
                           audit_log_id varchar(20) PRIMARY KEY,

    -- 操作者ID（可以是用户ID或系统进程）
                           operator_id varchar(20),

    -- 操作类型，例如：create, update, delete 等
                           operation_type VARCHAR(50) NOT NULL,

    -- 操作对象（例如：账户、交易等）
                           operation_object VARCHAR(100) NOT NULL,

    -- 操作时间戳，默认为当前时间
                           operation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- 操作详情
                           details TEXT,

    -- IP地址或其他上下文信息
                           context_info TEXT
);

-- 创建登录日志表，记录每次用户的登录信息
CREATE TABLE login_log (
    -- 登录日志唯一标识符，默认生成随机UUID并转换为二进制格式
                           login_id varchar(20) PRIMARY KEY  COMMENT '登录日志唯一标识符，默认生成随机UUID并转换为二进制格式',

    -- 关联的账户ID
                           account_id varchar(20) NOT NULL COMMENT '关联的账户ID',

    -- 用户IP地址
                           ip_address VARCHAR(45) NOT NULL COMMENT '用户IP地址，支持IPv4和IPv6',

    -- 浏览器信息
                           browser VARCHAR(255) COMMENT '浏览器信息，例如：Chrome, Firefox 等',

    -- 操作系统信息
                           os VARCHAR(255) COMMENT '操作系统信息，例如：Windows, macOS, Linux 等',

    -- 访问设备信息
                           device VARCHAR(255) COMMENT '访问设备信息，例如：Desktop, Mobile, Tablet 等',

    -- 登录时间戳，默认为当前时间
                           login_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间戳，默认为当前时间',

    -- 逻辑删除标志，默认为0表示未删除
                           del_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，默认为0表示未删除 1表示已经删除'
);

