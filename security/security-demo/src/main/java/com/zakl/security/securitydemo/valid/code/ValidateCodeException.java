package com.zakl.security.securitydemo.valid.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @program: security
 * @description: 验证码验证异常
 * @author: Mr.Wang
 * @create: 2019-03-18 20:43
 **/
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
