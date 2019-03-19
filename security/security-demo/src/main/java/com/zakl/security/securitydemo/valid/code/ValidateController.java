package com.zakl.security.securitydemo.valid.code;

import com.zakl.security.securitydemo.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
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

    @Autowired
    private SecurityProperties securityProperties;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = imageCodeGenerator.createImageCode(request);
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        //将图片信息返回给前端
        ImageIO.write(imageCode.getImage(), "JPG", response.getOutputStream());
    }


}
