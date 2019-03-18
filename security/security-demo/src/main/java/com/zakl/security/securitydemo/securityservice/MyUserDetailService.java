package com.zakl.security.securitydemo.securityservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @program: security
 * @description:
 * @author: Zakl
 * @create: 2019-03-17 19:37
 **/

@Component
public class MyUserDetailService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired //用来处理密码加密解密
    private PasswordEncoder passwordEncoder;
    @Override  //UserDetails是处理用户校验逻辑的接口
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        String encodedpassword = passwordEncoder.encode("123456");

        logger.info(name+"正在登陆");
        logger.info("数据库中的密码为"+encodedpassword);
        return new User(name, encodedpassword,true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

    }
}
