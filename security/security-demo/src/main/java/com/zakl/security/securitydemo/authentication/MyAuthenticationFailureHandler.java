package com.zakl.security.securitydemo.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zakl.security.securitydemo.config.SecurityPropertiesConfig;
import com.zakl.security.securitydemo.dto.ResultMessage;
import com.zakl.security.securitydemo.properties.LoginType;
import com.zakl.security.securitydemo.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: security
 * @description: 登录失败处理
 * @author: Mr.Wang
 * @create: 2019-03-18 16:59
 **/
@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties  securityProperties;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        logger.info("登录失败");

        if (securityProperties.getBroswer().getLoginType() == LoginType.JSON) {
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(new ResultMessage("1000",e.getMessage())));
        }
        else {
            super.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
        }




    }
}
