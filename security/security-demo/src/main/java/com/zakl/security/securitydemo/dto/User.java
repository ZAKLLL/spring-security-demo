package com.zakl.security.securitydemo.dto;

import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotBlank;

public class User {

    public interface UserSimpleView { }

    public interface UserDetailView extends UserSimpleView {

    }
    private String username;

    @NotBlank(message = "密码不能为空") //配合@Valid注解使用，表示该值不能为空，将在传入时进行校验
    private String password;

    private Long id;

    //表示返回的值能获取到id
    @JsonView(UserSimpleView.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User() {
    }



    //表示返回的值能获取到password
    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //表示返回的值只能获取到password，但因为UserDetailView接口继承了UserSimpleView所以可以获取到username
    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
