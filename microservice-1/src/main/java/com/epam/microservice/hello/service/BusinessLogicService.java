package com.epam.microservice.hello.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource(value = "messages.properties")
@RefreshScope
@EnableCircuitBreaker
public class BusinessLogicService {

    @Value("${email.subject}")
    private String text;

    private volatile long countDown = 10L;

    @HystrixCommand(defaultFallback = "defaultMethod")
    public synchronized String returnSmth() {
        countDown--;
        if (countDown == 0) {
            countDown = 10;
            throw new RuntimeException("Oops. You have exceeded number of attempts");
        }
        return text;
    }

    public String defaultMethod() {
        return "Please stop DDOSing";
    }
}
