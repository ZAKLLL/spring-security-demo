package com.zakl.security.securitydemo.valid.code.sms;

/**
 * @program: security
 * @description: 模拟短信服务发送
 * @author: Mr.Wang
 * @create: 2019-03-19 21:19
 **/
public interface SmsSender {
    void send(String mobile, String code);
}
