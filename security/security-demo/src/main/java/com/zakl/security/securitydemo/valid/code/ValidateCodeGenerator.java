package com.zakl.security.securitydemo.valid.code;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: security
 * @description: 图形验证码逻辑拓展接口
 * @author: Mr.Wang
 * @create: 2019-03-19 15:36
 **/
public interface ValidateCodeGenerator {
    ImageCode createImageCode(HttpServletRequest request);
}
