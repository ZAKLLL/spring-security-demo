package com.zakl.security.securitydemo.valid.code;

import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @program: security
 * @description: 图形验证码实体类
 * @author: Mr.Wang
 * @create: 2019-03-19 19:24
 **/
public class ValidateCode {
    private String code;
    private LocalDateTime expireTime; //过期时间点


    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);  //设置图形验证码过期时间点
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    //验证是否过期
    public boolean isExpried() {

        return LocalDateTime.now().isAfter(expireTime);
    }
}