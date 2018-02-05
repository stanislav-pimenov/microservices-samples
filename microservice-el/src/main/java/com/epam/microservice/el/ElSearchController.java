package com.epam.microservice.el;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ElSearchController {

    PercolateService percolateService;

    public ElSearchController(@Qualifier("rTemplate") PercolateService percolateService) {
        this.percolateService = percolateService;
    }

    @RequestMapping(method = GET,
            value = "/percolate",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String percolate(@RequestParam(value = "text") String text) throws Exception {

        return "Hits: " + String.valueOf(percolateService.percolate(text));
    }

}
