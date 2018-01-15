package com.epam.microservice.hello.consumer;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("microservice-1")
public interface HelloClient {
    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    String askForHello();
}