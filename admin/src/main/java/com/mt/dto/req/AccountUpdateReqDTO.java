package com.mt.dto.req;

import lombok.Data;


/**
 * 更新账户信息
 */
@Data
public class AccountUpdateReqDTO {
    private String address;
    private String phoneNumber;
}
