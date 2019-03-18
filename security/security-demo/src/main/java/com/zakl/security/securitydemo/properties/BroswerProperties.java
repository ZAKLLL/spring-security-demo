package com.zakl.security.securitydemo.properties;

import org.springframework.stereotype.Component;

/**
 * @program: security
 * @description: 浏览器访问配置参数
 * @author: Zakl
 * @create: 2019-03-17 22:14
 **/

public class BroswerProperties {
    private String loginPage="/signIn.html";

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
