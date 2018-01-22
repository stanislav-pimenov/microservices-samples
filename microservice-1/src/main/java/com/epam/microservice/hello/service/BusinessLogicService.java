package com.epam.microservice.hello.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@PropertySource(value = "messages.properties")
@RefreshScope
@EnableCircuitBreaker
public class BusinessLogicService {

    Logger logger = LoggerFactory.getLogger(BusinessLogicService.class);

    @Value("${email.subject}")
    private String text;

    private volatile long countDown = 10L;
/*
http://cloud.spring.io/spring-cloud-netflix/single/spring-cloud-netflix.html#_circuit_breaker_hystrix_clients

A service failure in the lower level of services can cause cascading failure all the way up to the user.
When calls to a particular service is greater than circuitBreaker.requestVolumeThreshold (default: 20 requests) and
failue percentage is greater than circuitBreaker.errorThresholdPercentage (default: >50%) in a rolling window defined
by metrics.rollingStats.timeInMilliseconds (default: 10 seconds), the circuit opens and the call is not made.
In cases of error and an open circuit a fallback can be provided by the developer.
 */
    @HystrixCommand(defaultFallback = "defaultMethod")
    public String returnSmth() {
        countDown--;
        if (countDown < 0) {
            //countDown = 10;
            logger.error("rejected");
            throw new RuntimeException("Oops. You have exceeded number of attempts");
        }
        logger.info("success");
        return text;
    }

    public String defaultMethod() {
        logger.info("default method");
        return "Please stop DDOSing";
    }

    public void reset() {
        logger.info("make service work again");
        countDown = 10;
    }
}
