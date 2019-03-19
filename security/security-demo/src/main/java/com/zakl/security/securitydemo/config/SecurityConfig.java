package com.zakl.security.securitydemo.config;

import com.zakl.security.securitydemo.authentication.MyAuthenticationFailureHandler;
import com.zakl.security.securitydemo.authentication.MyAuthenticationSuccessHandler;
import com.zakl.security.securitydemo.properties.SecurityProperties;
import com.zakl.security.securitydemo.valid.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private SecurityProperties properties;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        //将自定义的登录异常处理器放入图形验证码处理器
        validateCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);

        httpSecurity
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)//将自定义的图形验证过滤器放置在SpringSecurity登录过滤器之前；
                .formLogin()
                .loginPage("/authentication/require") //跳转到SecurityController中进行判断处理
                .loginProcessingUrl("/authentication/form") //处理表单发起的请求
                .successHandler(myAuthenticationSuccessHandler)//使用自定义的请求成功处理器
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                //表示当匹配到该路径时，不需要进行身份验证,验证码请求，跳转到SecurityController的请求，登录页面的请求
                .mvcMatchers("/authentication/require", properties.getBroswer().getLoginPage(),"/code/image").permitAll()
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
