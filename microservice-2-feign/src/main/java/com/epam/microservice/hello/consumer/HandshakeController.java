package com.epam.microservice.hello.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HandshakeController {

    @Autowired
    HelloClient helloClient;

    @RequestMapping(path = "/test")
    String doSmth() {
        return helloClient.askForHello();
    }
}
