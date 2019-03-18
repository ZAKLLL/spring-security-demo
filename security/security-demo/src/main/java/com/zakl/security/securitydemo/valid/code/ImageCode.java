package com.zakl.security.securitydemo.valid.code;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @program: security
 * @description: 图形验证码信息
 * @author: Mr.Wang
 * @create: 2019-03-18 19:50
 **/
public class ImageCode {
    private BufferedImage image;
    private String code;
    private LocalDateTime expireTime; //过期时间点



    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        this.image = image;
        this.code = code;
        this.expireTime = expireTime;
    }
    public ImageCode(BufferedImage image, String code, int  expireIn) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);  //设置图形验证码过期时间点
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    //验证是否过期
    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
