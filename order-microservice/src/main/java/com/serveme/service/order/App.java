package com.serveme.service.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;


@Controller
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan("com.serveme.service.order")
@EnableFeignClients("com.serveme.service.order.external.service")
@EnableDiscoveryClient
public class App {
    public static void main(String[] args) throws Exception {

        String environment = getSpringProfile(args);
        System.setProperty("spring.config.name", "order-service-"+ environment);
        SpringApplication.run(App.class, args);

    }

    private static String getSpringProfile(String[] args){
        if(args != null) {
            for(String arg : args) {
                if (arg.startsWith("--spring.profiles.active=")) {
                    return arg.substring("--spring.profiles.active=".length());
                }
            }
        }

        return "dev";
    }




}
