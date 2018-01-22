package com.epam.microservice.hello.consumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableCircuitBreaker
public class HandshakeController {

    @Autowired
    HelloClient helloClient;

    @RequestMapping(path = "/test")
    @HystrixCommand
    String doSmth() {
        return helloClient.askForHello();
    }
}
