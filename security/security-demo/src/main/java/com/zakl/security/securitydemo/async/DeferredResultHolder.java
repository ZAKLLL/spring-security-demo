package com.zakl.security.securitydemo.async;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: security
 * @description:
 * @author: Zakl
 * @create: 2019-03-17 21:13
 **/
@Component
public class DeferredResultHolder {
    Map<String, DeferredResult<String>> map = new HashMap<>();

    public Map<String, DeferredResult<String>> getMap() {
        return map;
    }

    public void setMap(Map<String, DeferredResult<String>> map) {
        this.map = map;
    }
}
