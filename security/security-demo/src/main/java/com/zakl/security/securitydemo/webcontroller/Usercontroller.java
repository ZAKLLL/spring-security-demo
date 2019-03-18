package com.zakl.security.securitydemo.webcontroller;

import com.fasterxml.jackson.annotation.JsonView;
import com.zakl.security.securitydemo.dto.User;
import com.zakl.security.securitydemo.errror.UserNotExistException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "aaaaaaa", description = "用户接口文档")
public class Usercontroller {

    @GetMapping("/user")
    @ResponseBody
    @ApiOperation("swagger测试接口")
    @JsonView(User.UserSimpleView.class)
    public List<User> test() {
        List<User> users = new ArrayList<>();
        users.add(new User("张三", "123456"));
        users.add(new User("李四", "123456"));
        users.add(new User("王五", "123456"));
        return users;
    }

    @GetMapping("/user/{id:\\d+}") //使用正则表达式表示仅接收数字类型的参数
    @ResponseBody
    @ApiOperation("pathvariable风格")
    @JsonView(User.UserDetailView.class) //表示返回User中的Userdetail细节，需要在User类中定义的对应的接口
    public User queryUserById(@PathVariable("id") String id) {
        if(Integer.parseInt(id)==1){
            throw new UserNotExistException((long) Integer.parseInt(id));
        }
        User user = new User("张三", "王二");
        return user;
    }

    @PostMapping("/create")
    //使用@Valid 与BindingResult 进行配合使用，当校验失败时候，会将错误信息放入errors
    public void create(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error->System.out.println(error.getDefaultMessage()));
        }
        System.out.println(user.getId() + " " + user.getUsername() + " " + user.getPassword());
    }

    @PutMapping("/update")
    public void update(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error->System.out.println(error.getDefaultMessage()));
        }
        System.out.println(user.getUsername());
    }

}
