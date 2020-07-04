package com.sixj.hystrix.provider.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author sixiaojie
 * @date 2020-07-01-16:41
 */
@FeignClient(name = "hystrix-provider")
public interface HystrixApi {

    @GetMapping("/HystrixApi/handleSuccess")
    String handleSuccess();

    @GetMapping("/HystrixApi/handleTimeOut")
    String handleTimeOut();
}
