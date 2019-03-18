package com.zakl.security.securitydemo.config;

import com.zakl.security.securitydemo.fliter.TimeFliter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/*
    第三方filter注册到spring中，传统web项目中的web.xml中编写
 */

//@Configuration   开启第三方filter的时候需要开启此注解，并将第三方filter注册到spring容器中
public class FilterConfig {

    @Bean
    public FilterRegistrationBean timeFilter(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new TimeFliter());
        List<String> urls = new ArrayList<>();
        urls.add("/*"); //对所有的路径访问都进行过滤处理
        filterRegistrationBean.setUrlPatterns(urls);

        return filterRegistrationBean;
    }


}
