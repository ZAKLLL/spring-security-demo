package com.zakl.security.securitydemo.webcontroller;

import com.zakl.security.securitydemo.errror.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/*
    自定义异常处理
 */
@ControllerAdvice       //声明该类是用来异常处理的
public class UserExceptionHandler {


    @ExceptionHandler(UserNotExistException.class)  //申明处理所有的UserNotExistException错误
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> usernotexist(UserNotExistException ex){
        Map<String, Object> map = new HashMap<>();
        map.put("id", ex.getId());
        map.put("errmsg", ex.getMessage());
        return map;
    }

}
