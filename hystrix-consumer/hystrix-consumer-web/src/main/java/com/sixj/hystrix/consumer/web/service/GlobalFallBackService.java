package com.sixj.hystrix.consumer.web.service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sixj.hystrix.provider.api.HystrixApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sixiaojie
 * @date 2020-07-05-09:34
 */
@Service
@DefaultProperties(defaultFallback = "globalFallBackMethod")
public class GlobalFallBackService {
    @Autowired
    private HystrixApi hystrixApi;

    /**
     *模拟超时，使用全局异常处理
     * @return
     */
    @HystrixCommand
    public String handleTimeOut() {
        String s = hystrixApi.handleTimeOut();
        return s;
    }

    private String globalFallBackMethod(){
        return "Global异常处理信息，请稍后重试";
    }
}
