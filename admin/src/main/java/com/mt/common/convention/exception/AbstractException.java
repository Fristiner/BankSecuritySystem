package com.mt.common.convention.exception;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/22 14:24
 * {@code @project} BankSecuritySystem
 *
 */


import com.mt.common.convention.errorcode.IErrorCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        com.mt.common.convention.exception
 * {@code @className:}      AbstractException
 * {@code @author:}         ma
 * {@code @date:}           2024/12/22 14:24
 * {@code @description:}
 */
@Getter
public class AbstractException extends RuntimeException {
    public final String errorCode;

    public final String errorMessage;

    public AbstractException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable);
        this.errorCode = errorCode.code();
        this.errorMessage = Optional.ofNullable(StringUtils.hasLength(message) ? message : null).orElse(errorCode.message());
    }
}
