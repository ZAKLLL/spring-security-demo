package com.zakl.security.securitydemo.valid.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @program: security
 * @description: 图形验证码实体类
 * @author: Mr.Wang
 * @create: 2019-03-19 19:24
 **/
public class ImageCode extends ValidateCode {
    private BufferedImage image;


    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;

    }

    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}