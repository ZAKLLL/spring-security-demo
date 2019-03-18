package com.zakl.security.securitydemo.config;

import com.zakl.security.securitydemo.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private SecurityProperties properties;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin()
                .loginPage("/authentication/require") //跳转到SecurityController中进行判断处理
                .loginProcessingUrl("/authentication/form") //处理表单发起的请求
                .and()
                .authorizeRequests()
                //表示当匹配到该路径时，不需要进行身份验证
                .mvcMatchers("/authentication/require",properties.getBroswer().getLoginPage()).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable(); //暂时关闭跨域保护
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        //Todd 关闭spring security
//        web.ignoring().antMatchers("/**");
//
//    }
}
