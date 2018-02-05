package com.epam.microservice.el.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PercolateResponse {

    private Integer took;
    @JsonProperty("timed_out")
    private Boolean timeout;
    //    @JsonProperty("_shards")
    //    public Shards shards;
    private Hits hits;

    public Integer getTook() {
        return took;
    }

    public void setTook(Integer took) {
        this.took = took;
    }

    public Boolean getTimeout() {
        return timeout;
    }

    public void setTimeout(Boolean timeout) {
        this.timeout = timeout;
    }

    public Hits getHits() {
        return hits;
    }

    public void setHits(Hits hits) {
        this.hits = hits;
    }
}
