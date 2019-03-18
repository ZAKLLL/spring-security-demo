package com.zakl.security.securitydemo.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: security
 * @description: 模拟消息队列
 * @author: Zakl
 * @create: 2019-03-17 21:15
 **/

@Component
public class OrderQueue {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private String placeOrder;
    private String completeOrder;

    public String getPlaceOrder() {
        return placeOrder;
    }
    public  void setPlaceOrder(String placeOrder) throws InterruptedException {
        new Thread(()->{
            logger.info("消息队列线程开始启动");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.completeOrder = placeOrder;
            logger.info("消息队列线程结束");

        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }

}
