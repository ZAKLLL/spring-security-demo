package com.zakl.security.securitydemo.properties;

import org.springframework.stereotype.Component;

/**
 * @program: security
 * @description: 浏览器访问配置参数
 * @author: Zakl
 * @create: 2019-03-17 22:14
 **/

public class BroswerProperties {
    private String loginPage = "/signIn.html";

    private LoginType loginType = LoginType.JSON;

    private int rememberMeSeconds = 3600;

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getLoginPage() {
        return loginPage;
    }
}
