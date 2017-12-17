package com.serveme.service.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;


@Controller
@EnableAutoConfiguration
@EnableScheduling
@EnableDiscoveryClient
@ComponentScan("com.serveme.service")
public class App 
{

	public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

}
