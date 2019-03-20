package com.zakl.security.securitydemo.properties;

/**
 * @program: security
 * @description: 应用级图形验证码参数配置
 * @author: Mr.Wang
 * @create: 2019-03-19 10:00
 **/
public class ImageCodeProperties extends SmsCodeProperties {
    private int width = 70; // 宽
    private int height = 35; // 高

    public ImageCodeProperties(){
        setLength(4);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
