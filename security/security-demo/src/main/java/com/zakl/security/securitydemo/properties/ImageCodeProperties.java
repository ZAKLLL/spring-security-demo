package com.zakl.security.securitydemo.properties;

/**
 * @program: security
 * @description: 应用级图形验证码参数配置
 * @author: Mr.Wang
 * @create: 2019-03-19 10:00
 **/
public class ImageCodeProperties {
    private int width = 70; // 宽
    private int height = 35; // 高
    private int length=4;  //长度
    private int expireIn=30; //过期时间

    private String url; //需要被验证码过滤器拦截的url

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }
}
