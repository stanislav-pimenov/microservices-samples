package com.epam.microservice.el.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.Map;

/**
 * Created by Stanislav_Pimenov on 05.02.2018.
 */

public class Shards {

    private Map<String, String> properties;

    @JsonAnySetter
    public void add(String key, String value) {
        properties.put(key, value);
    }
}
