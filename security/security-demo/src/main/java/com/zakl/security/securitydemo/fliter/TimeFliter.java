package com.zakl.security.securitydemo.fliter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

//@Component
public class TimeFliter  implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("TimeFilter初始化成功");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Long start=new Date().getTime();
        System.out.println("正在执行TimeFilterd过滤操作");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("TimeFilter执行完毕，耗时："+ (new Date().getTime()-start));
    }

    @Override
    public void destroy() {
        System.out.println("TimeFilter destroy成功");
    }
}
