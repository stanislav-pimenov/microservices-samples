package com.epam.microservice.hello.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource(value = "messages.properties")
@RefreshScope
public class BusinessLogicService {

    @Value("${email.subject}")
    private String text;

    public String returnSmth() {
        return text;
    }
}
