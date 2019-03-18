package com.zakl.security.securitydemo.valid.code;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @program: security
 * @description: 验证码过滤器，放在SpringSecurity验证之前用以验证码匹配
 * @author: Mr.Wang
 * @create: 2019-03-18 20:33
 **/
public class ValidateCodeFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();

    private AuthenticationFailureHandler authenticationFailureHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        logger.info("进入图形验证过滤器");

        //当请求中包含登录请求时候进行验证,当
        if (StringUtils.equals("/authentication/form", httpServletRequest.getRequestURI())
                && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), "post")) {
            try {
                //验证封装后的httpServletRequest
                validate(new ServletWebRequest(httpServletRequest));
            }catch (ValidateCodeException e){
                //用登录失败处理器处理验证码匹配异常
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
            }

        }


        doFilter(httpServletRequest,httpServletResponse,filterChain);

    }

    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        //从session中取出imageCode
        ImageCode imageCodeInSession =(ImageCode)sessionStrategy.getAttribute(servletWebRequest, ValidateController.SESSION_KEY);

        //从request中取出imageCode
        String imageCodeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");

        if (StringUtils.isBlank(imageCodeInRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (imageCodeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (imageCodeInSession.isExpried()) {
            //清空过期的验证码
            sessionStrategy.removeAttribute(servletWebRequest,ValidateController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }
        if (StringUtils.equals(imageCodeInSession.getCode(),imageCodeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        //验证完毕后清空session中的验证码
        sessionStrategy.removeAttribute(servletWebRequest,ValidateController.SESSION_KEY);
    }

    public SessionStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
