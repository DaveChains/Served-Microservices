package com.millennialslab.served.service;

import com.millennialslab.served.service.domain.UserDomain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;


@Controller
@EnableAutoConfiguration
@ComponentScan("com.millennialslab.served.service")
@SpringBootApplication
@EnableDiscoveryClient
//@EnableZuulProxy
@EnableHystrix
public class App
{

    public static void main(String[] args) throws Exception {

        SpringApplication.run(App.class, args);
    }

    @Bean
    public ResourceProcessor<Resource<UserDomain>> movieProcessor() {
        return new ResourceProcessor<Resource<UserDomain>>() {
            @Override
            public Resource<UserDomain> process(Resource<UserDomain> resource) {

                resource.add(new Link("/userssss/test", "tests"));
                return resource;
            }
        };
    }

}
