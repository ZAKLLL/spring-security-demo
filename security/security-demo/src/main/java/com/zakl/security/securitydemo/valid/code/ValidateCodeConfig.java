package com.zakl.security.securitydemo.valid.code;

import com.zakl.security.securitydemo.properties.SecurityProperties;
import com.zakl.security.securitydemo.properties.ValidateCodeProterties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: security
 * @description: 用来配置拓展的图形验证码生成逻辑
 * @author: Mr.Wang
 * @create: 2019-03-19 16:15
 **/
@Configuration
public class ValidateCodeConfig {

    @Autowired
    private SecurityProperties securityProperties;


    //当其他实现了ValidateCodeGenerator接口生成验证码的实现类，
    // 一旦在注册为bean时候将id设置为"imageCodeGenerator",则新的实现类将覆盖本默认实现类。
    @Bean
    @ConditionalOnMissingBean(name="imageCodeGenerator")
    public ValidateCodeGenerator codeGenerator(){
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return imageCodeGenerator;
    }
}
