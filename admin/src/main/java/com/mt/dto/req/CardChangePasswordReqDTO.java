package com.mt.dto.req;

import lombok.Data;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.dto.req
 * {@code @author:}         ma
 * {@code @date:}           2025-01-01 下午11:07
 * {@code @description:}
 */
@Data
public class CardChangePasswordReqDTO {
    private String newPassword;
    private String oldPassword;
}
