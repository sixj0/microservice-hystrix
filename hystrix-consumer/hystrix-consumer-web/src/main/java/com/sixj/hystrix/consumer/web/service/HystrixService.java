package com.sixj.hystrix.consumer.web.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sixj.hystrix.provider.api.HystrixApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sixiaojie
 * @date 2020-07-01-16:59
 */
@Service
public class HystrixService {
    @Autowired
    private HystrixApi hystrixApi;

    public String handleSuccess(){
        String handle = hystrixApi.handleSuccess();
        return handle;
    }

    /**
     *模拟超时，启用备用方案
     * @return
     */
    @HystrixCommand(
            commandProperties = {
                    // HystrixCommandProperties
                    // 设置超时时间
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000"),
                    // 三秒钟内，请求次数达到2次，并且失败率在百分之50以上，就跳闸，
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "3000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "2"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "50"),
                    // 跳闸后的活动窗口设置为3秒
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "3000")
            },
            // 备选方案方法名
            fallbackMethod = "fallback0",
            // 根据threadPoolKey区分线程池
            threadPoolKey = "handleTimeOut",
            threadPoolProperties = {
                    // HystrixThreadPoolProperties
                    // 线程池配置
                    @HystrixProperty(name = "coreSize",value = "2"),
                    @HystrixProperty(name = "maxQueueSize",value = "20")
            }
    )
    public String handleTimeOut() {
        String s = hystrixApi.handleTimeOut();
        return s;
    }

    public String fallback0(){
        return "请求超时，启动备用方案！";
    }
}
