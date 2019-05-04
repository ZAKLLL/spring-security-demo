package com.zakl.security.securitydemo.config;

import com.zakl.security.securitydemo.authentication.MyAuthenticationFailureHandler;
import com.zakl.security.securitydemo.authentication.MyAuthenticationSuccessHandler;
import com.zakl.security.securitydemo.authentication.mobile.SmsAuthenticationConfig;
import com.zakl.security.securitydemo.authentication.mobile.SmsCodeFilter;
import com.zakl.security.securitydemo.authentication.session.ExpriedSessionStrategy;
import com.zakl.security.securitydemo.properties.SecurityProperties;
import com.zakl.security.securitydemo.valid.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //注册密码加密工具类
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    //记住我功能
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //jdbcTokenRepository.setCreateTableOnStartup(true); //设定为让JDBC自动生成数据库中的表仅第一次启动时候创建
        return jdbcTokenRepository;
    }

    @Autowired
    private SecurityProperties properties;

    @Autowired
    private SmsAuthenticationConfig smsAuthenticationConfig;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService myUserDetailService;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        //将自定义的登录异常处理器放入图形验证码处理器
        validateCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(properties);
        validateCodeFilter.afterPropertiesSet();


        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        //将自定义的登录异常处理器放入图形验证码处理器
        smsCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        smsCodeFilter.setSecurityProperties(properties);
        smsCodeFilter.afterPropertiesSet();


        httpSecurity
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)//将短信验证码加到UsernamePasswordAuthenticationFilter之前
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)//将自定义的图形验证过滤器放置在SpringSecurity登录过滤器之前；
                .formLogin()
                .loginPage("/authentication/require") //每一个请求都会跳转到SecurityController中进行判断处理
                .loginProcessingUrl("/authentication/form") //处理表单发起的请求
                .successHandler(myAuthenticationSuccessHandler)//使用自定义的请求成功处理器
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(properties.getBroswer().getRememberMeSeconds())
                .userDetailsService(myUserDetailService)
                .and()
                .logout()
                //.logoutSuccessUrl()//退出成功后返回的url，要在下面的匹配路径授权
                //.logoutSuccessHandler() //退出成功后的处理，需要自定义处理器
                .deleteCookies()
                .and()
                .sessionManagement()
                .invalidSessionUrl("/session/invalid")//当session超时的时候访问"/session/invalid"该url做对应的处理操作
                .maximumSessions(1)//设置每个的用户最大session数量
                .expiredSessionStrategy(new ExpriedSessionStrategy())//并发登录处理
                .maxSessionsPreventsLogin(true) //当用户session数量达到最大时候，禁止新的session产生,阻止新的登录产生
                .and()
                .and()
                .authorizeRequests()
                //表示当匹配到该路径时，不需要进行身份验证,验证码请求，跳转到SecurityController的请求，登录页面的请求
                .mvcMatchers("/authentication/require",
                        properties.getBroswer().getLoginPage(),
                        "/code/image",
                        "code/smsCode",
                        "/authentication/mobile", "/session/invalid").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable() //暂时关闭跨域保护
                .apply(smsAuthenticationConfig);

    }

}
