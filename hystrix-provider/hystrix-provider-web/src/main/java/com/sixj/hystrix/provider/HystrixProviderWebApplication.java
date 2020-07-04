package com.sixj.hystrix.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.sixj.hystrix"})
@EnableDiscoveryClient
public class HystrixProviderWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixProviderWebApplication.class, args);
    }

}
