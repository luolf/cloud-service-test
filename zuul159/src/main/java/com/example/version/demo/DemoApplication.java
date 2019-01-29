package com.example.version.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
@EnableZuulProxy
@EnableEurekaClient
public class DemoApplication {
    @Value("${server.port}")
    int port;
    @Value("${spring.application.name}")
    String serviceName;
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @RequestMapping("/hi")
    public String sayHi(){
        Map<String, String> map = System.getenv();
        return "HI I\'M "+map.get("COMPUTERNAME")+serviceName+":"+port;
    }

}

