package com.zakl.security.securitydemo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: security
 * @description: 读取application.properties中的配置信息
 * @author: Zakl
 * @create: 2019-03-17 22:01
 **/
@ConfigurationProperties(prefix = "zakl.security")
public class SecurityProperties {

    private BroswerProperties broswer = new BroswerProperties();
    private ValidateCodeProterties code=new ValidateCodeProterties();


    public ValidateCodeProterties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProterties code) {
        this.code = code;
    }

    public BroswerProperties getBroswer() {
        return broswer;
    }
    public void setBroswer(BroswerProperties broswer) {
        this.broswer = broswer;
    }


}
