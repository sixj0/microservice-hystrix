package com.sixj.hystrix.provider.api;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author sixiaojie
 * @date 2020-07-01-16:45
 */
@RestController
@RequestMapping("/HystrixApi")
public class HystrixApiImpl implements HystrixApi {

    @Override
    @GetMapping("/handleSuccess")
    public String handleSuccess() {
        return "成功=====";
    }

    @Override
    @GetMapping("/handleTimeOut")
    public String handleTimeOut() {
        Integer sleepTime = RandomUtils.nextInt(3);
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "耗时："+sleepTime+"秒";
    }
}
