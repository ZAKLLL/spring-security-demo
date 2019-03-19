package com.zakl.security.securitydemo.properties;

/**
 * @program: security
 * @description: 各类验证码配置类，图形验证码，短信验证码之类的
 * @author: Mr.Wang
 * @create: 2019-03-19 10:05
 **/
public class ValidateCodeProterties {
    private ImageCodeProperties image = new ImageCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
}
