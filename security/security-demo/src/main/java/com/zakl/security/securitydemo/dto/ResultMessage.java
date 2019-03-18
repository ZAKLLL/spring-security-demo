package com.zakl.security.securitydemo.dto;

/**
 * @program: security
 * @description: 统一返回对象
 * @author: Zakl
 * @create: 2019-03-17 20:56
 **/

public class ResultMessage {
    private String code;
    private Object object;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public ResultMessage(String code, Object object) {
        this.code = code;
        this.object = object;
    }
}
