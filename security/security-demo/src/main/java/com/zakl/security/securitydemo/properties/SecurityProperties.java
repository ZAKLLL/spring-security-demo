package com.zakl.security.securitydemo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: security
 * @description: 配置文件类
 * @author: Zakl
 * @create: 2019-03-17 22:01
 **/
@ConfigurationProperties(prefix = "zakl.security")
public class SecurityProperties {

    private BroswerProperties broswer = new BroswerProperties();

    public BroswerProperties getBroswer() {
        return broswer;
    }

    public void setBroswer(BroswerProperties broswer) {
        this.broswer = broswer;
    }
}
