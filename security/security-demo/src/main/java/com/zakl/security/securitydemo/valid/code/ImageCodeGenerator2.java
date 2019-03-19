package com.zakl.security.securitydemo.valid.code;

import org.apache.commons.collections.set.SynchronizedSortedSet;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: security
 * @description: 测试覆盖默认图片生成类
 * @author: Mr.Wang
 * @create: 2019-03-19 19:27
 **/

//@Component("imageCodeGenerator")
public class ImageCodeGenerator2 implements ValidateCodeGenerator {
    @Override
    public ImageCode createImageCode(HttpServletRequest request) {
        System.out.println("使用更高级的图片生成器");
        return null;
    }
}
