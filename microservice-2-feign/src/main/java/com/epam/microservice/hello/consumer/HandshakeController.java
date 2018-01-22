package com.epam.microservice.hello.consumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@EnableCircuitBreaker
public class HandshakeController {

    @Autowired
    HelloClient helloClient;

    @RequestMapping(path = "/test")
    @HystrixCommand
    String doSmth() {
        //return helloClient.askForHello();
        for (int i=0; i< 30; i++) {
            helloClient.askForHello();
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "ok";
    }
}
