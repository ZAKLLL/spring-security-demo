package com.zakl.security.securitydemo.properties;

/**
 * @program: security
 * @description: 短信验证码的配置
 * @author: Mr.Wang
 * @create: 2019-03-20 08:00
 **/
public class
SmsCodeProperties {
    private int length=6;
    private int expireIn=6;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }
}