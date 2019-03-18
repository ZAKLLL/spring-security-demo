package com.zakl.security.securitydemo.valid.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

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

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = createImageCode(request);
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);
        //将图片信息返回给前端
        ImageIO.write(imageCode.getImage(), "JEPG", response.getOutputStream());
    }


    //生成图形码
    private ImageCode createImageCode(HttpServletRequest request) {
        ImageImpl image=new ImageImpl();
        BufferedImage bufferedImage=image.getImage();
        String code=image.chars;
        logger.info("验证码是:"+code);

        return new ImageCode(bufferedImage,code,30);
    }

}
