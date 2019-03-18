package com.zakl.security.securitydemo.config;

import com.zakl.security.securitydemo.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: security
 * @description: 将SecurityProperties生效
 * @author: Zakl
 * @create: 2019-03-17 22:17
 **/
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityPropertiesConfig {

}
