package com.epam.microservice.hello;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@Configuration
//@ComponentScan
//@EnableAutoConfiguration

@SpringBootApplication
@EnableEurekaClient
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }
}
