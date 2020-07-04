package com.sixj.hystrix.consumer.web.controller;

import com.sixj.hystrix.consumer.web.service.HystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sixiaojie
 * @date 2020-07-01-16:58
 */
@RestController
@RequestMapping("/hystrix")
public class HystrixTestController {

    @Autowired
    private HystrixService hystrixService;

    @GetMapping("/handleSuccess")
    public String handleSuccess(){
        String s = hystrixService.handleSuccess();
        return s;
    }

    @GetMapping("/handleTimeOut")
    public String handleTimeOut(){
        String s = hystrixService.handleTimeOut();
        return s;
    }
}
