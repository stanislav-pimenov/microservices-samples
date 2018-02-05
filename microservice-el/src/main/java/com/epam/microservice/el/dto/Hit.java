package com.epam.microservice.el.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Hit {

    @JsonProperty("_index")
    public String index;
    @JsonProperty("_type")
    public String type;
    @JsonProperty("_id")
    public String id;
    @JsonProperty("_score")
    public BigDecimal score;

    /*
    {
                "_index": "news_index",
                "_type": "doc",
                "_id": "1",
                "_score": 0.5753642,
                "_source": {
                    "query": {
                        "match": {
                            "message": "bonsai tree"
                        }
                    }
                },
                "fields": {
                    "_percolator_document_slot": [
                        0
                    ]
                }
            },
     */
}
