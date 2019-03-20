package com.zakl.security.securitydemo.valid.code;

import com.zakl.security.securitydemo.properties.SecurityProperties;
import com.zakl.security.securitydemo.valid.code.sms.SmsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: security
 * @description: 用来返回验证码的接口
 * @author: Mr.Wang
 * @create: 2019-03-18 20:00
 **/

@RestController
public class ValidateController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private ValidateCodeGenerator defaultImageCodeGenerator;

    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;

    @Autowired
    private SmsSender defaultSmsSender;

    public static final String SESSION_KEY_IMAGE = "SESSION_KEY_IMAGE_CODE";

    public static final String SESSION_KEY_SMS = "SESSION_KEY_IMAGE_CODE";


    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = (ImageCode) defaultImageCodeGenerator.createValidateCode(request);
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_IMAGE, imageCode);
        //将图片信息返回给前端
        ImageIO.write(imageCode.getImage(), "JPG", response.getOutputStream());
    }

    @GetMapping("/code/smsCode")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        ValidateCode smsCode = smsCodeGenerator.createValidateCode(request);
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_SMS, smsCode);
        defaultSmsSender.send(ServletRequestUtils.getRequiredStringParameter(request, "mobile"), smsCode.getCode());
    }

}
