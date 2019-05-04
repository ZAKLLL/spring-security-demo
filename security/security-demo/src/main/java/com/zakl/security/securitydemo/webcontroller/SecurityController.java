package com.zakl.security.securitydemo.webcontroller;

import com.zakl.security.securitydemo.dto.ResultMessage;
import com.zakl.security.securitydemo.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: security
 * @description: 当需要认证的时候，跳转到这里
 * @author: Zakl
 * @create: 2019-03-17 20:38
 **/
@RestController
public class SecurityController {
    @Autowired
    private SecurityProperties properties;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();

    //请求转发工具类
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        @RequestMapping("/authentication/require")
        @ResponseBody
        @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
        public ResultMessage requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

            SavedRequest savedRequest = requestCache.getRequest(request, response);
            if (savedRequest !=null) {
                String targetUrl = savedRequest.getRedirectUrl();
                logger.info("引发跳转的请求是:" + targetUrl);
                if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                    redirectStrategy.sendRedirect(request,response,properties.getBroswer().getLoginPage());
                }

            }
        return new ResultMessage("20000","访问用户需要进行身份验证请引导用户进行登录");
    }

    @RequestMapping("/session/invalid")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResultMessage invalid() {
        String msg = "用户sessions失效";
        return new ResultMessage("100", msg);
    }

}
