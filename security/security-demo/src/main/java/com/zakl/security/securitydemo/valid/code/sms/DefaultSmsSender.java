package com.zakl.security.securitydemo.valid.code.sms;

/**
 * @program: security
 * @description: 模拟短信发送接口
 * @author: Mr.Wang
 * @create: 2019-03-19 21:21
 **/
public class DefaultSmsSender implements SmsSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println("像手机号为:"+mobile+" 的用户发送验证码:"+code);
    }
}
