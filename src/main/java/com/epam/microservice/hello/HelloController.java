package com.epam.microservice.hello;

import com.epam.microservice.hello.service.BusinessLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    private BusinessLogicService businessLogicService;

    @Autowired
    public HelloController(BusinessLogicService businessLogicService) {
        this.businessLogicService = businessLogicService;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return businessLogicService.returnSmth();
    }
}
