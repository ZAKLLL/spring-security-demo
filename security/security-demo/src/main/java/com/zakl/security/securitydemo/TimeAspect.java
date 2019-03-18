package com.zakl.security.securitydemo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @program: security
 * @description: aspect
 * @author: Mr.Wang
 * @create: 2019-03-16 17:21
 **/
//@Aspect
//@Component
public class TimeAspect  {

    @Around("execution(* com.zakl.security.securitydemo.webcontroller.Usercontroller.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint point) throws Throwable {
        System.out.println("TimeAspect start");
        Object[] objs = point.getArgs();
        for (Object obj:objs) {
            System.out.println("arg is "+obj);

        }
        Object object= point.proceed();
        System.out.println("TimeAspect end");
        return object;
    }
}
