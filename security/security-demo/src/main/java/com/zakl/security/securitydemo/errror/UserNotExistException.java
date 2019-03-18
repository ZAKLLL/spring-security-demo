package com.zakl.security.securitydemo.errror;


/*
自定义异常
 */

public class UserNotExistException extends RuntimeException {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserNotExistException(Long id) {
        super("用户不存在");
        this.id=id;
    }
}
