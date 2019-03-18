package com.zakl.security.securitydemo.async;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.event.spi.RefreshEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @program: security
 * @description: 模拟监听消息队列
 * @author: Zakl
 * @create: 2019-03-17 21:20
 **/

@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OrderQueue orderQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new Thread(() -> {

            while (true) {
                if (StringUtils.isNoneBlank(orderQueue.getCompleteOrder())) {
                    logger.info("监听器线程开始执行");
                    String orderNumber = orderQueue.getCompleteOrder();
                    deferredResultHolder.getMap().get(orderNumber).setResult("成功处理：" + orderNumber);
                    logger.info("监听器线程执行完毕");
                    orderQueue.setCompleteOrder(null);

                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
