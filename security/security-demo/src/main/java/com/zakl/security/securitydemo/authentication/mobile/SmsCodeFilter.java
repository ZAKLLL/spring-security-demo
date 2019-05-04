package com.zakl.security.securitydemo.authentication.mobile;

import com.zakl.security.securitydemo.properties.SecurityProperties;
import com.zakl.security.securitydemo.valid.code.ValidateCode;
import com.zakl.security.securitydemo.valid.code.ValidateCodeException;
import com.zakl.security.securitydemo.valid.code.ValidateController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * @program: security
 * @description: 验证码过滤器，放在SpringSecurity验证之前用以验证码匹配
 * @author: Mr.Wang
 * @create: 2019-03-18 20:33
 **/

//InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候都会执行该方法。
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private Set<String> urls = new HashSet<>();

    private SecurityProperties securityProperties;

    private AuthenticationFailureHandler authenticationFailureHandler;

    //spring提供的工具类，用来匹配url
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    //当传入参数初始化之后执行该方法,将配置信息中的需要拦截的url放置在urls中
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(
        securityProperties.getCode().getSms().getUrl(), ",");
        if (configUrls != null) {
            for (String configUrl : configUrls) {
                urls.add(configUrl);
            }
        }
        urls.add("/authentication/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        //循环判断，判断请求中的url是否和参数中配置的任何一个url相匹配，如果匹配，则进行验证码验证
        boolean action = false;
        for (String url : urls) {
            if (pathMatcher.match(url, httpServletRequest.getRequestURI())) {
                action = true;
            }
        }

        //当请求中包含登录请求时候进行验证,当
        if (StringUtils.equals("/authentication/mobile", httpServletRequest.getRequestURI())
                && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), "post")) {
            logger.info("开始进行sms验证码校验");
            try {
                //验证封装后的httpServletRequest
                validate(new ServletWebRequest(httpServletRequest));
            } catch (ValidateCodeException e) {
                //用登录失败处理器处理验证码匹配异常
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                //校验失败后直接返回不在进行登录业务操作
                return;
            }

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    //执行验证码校对逻辑，如果校对失败返回自定义异常
    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        //从session中取出smsCode
        ValidateCode validateCodeinsession = (ValidateCode) sessionStrategy.getAttribute(servletWebRequest, ValidateController.SESSION_KEY_SMS);

        //从request中取出smsCode
        String smsCode = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "smsCode");

        if (StringUtils.isBlank(smsCode)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (validateCodeinsession == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (validateCodeinsession.isExpried()) {
            //清空过期的验证码
            sessionStrategy.removeAttribute(servletWebRequest, ValidateController.SESSION_KEY_SMS);
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equalsIgnoreCase(validateCodeinsession.getCode(), smsCode)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        logger.info("短信验证码校验成功");
        //验证完毕后清空session中的验证码
        sessionStrategy.removeAttribute(servletWebRequest, ValidateController.SESSION_KEY_SMS);
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

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }
}
