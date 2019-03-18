package com.zakl.security.securitydemo.async;

import com.zakl.security.securitydemo.dto.ResultMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import sun.rmi.runtime.Log;

import java.util.concurrent.Callable;

/**
 * @program: security
 * @description: 多线程高性能的controller
 * @author: Zakl
 * @create: 2019-03-17 20:58
 **/
@RestController
public class AsyncController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping("/async/order")
    public Callable<String> order(@RequestParam("order") String order) {
        logger.info("主线程开始");
        Callable<String> result=new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("副线程开始执行");
                Thread.sleep(4000);
                logger.info("副线程执行完毕");
                return order;
            }
        };
        logger.info("主线程执行完毕");
        return result;
    }

    @Autowired
    private OrderQueue orderQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @ResponseBody
    @RequestMapping("/async/order2")
    public DeferredResult<String> order2() throws InterruptedException {
        logger.info("主线程开始");
        String ordernumber = RandomStringUtils.randomNumeric(8);

        orderQueue.setPlaceOrder(ordernumber);
        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(ordernumber, result);
        logger.info("主线程执行完毕");

        return result;



    }


}
