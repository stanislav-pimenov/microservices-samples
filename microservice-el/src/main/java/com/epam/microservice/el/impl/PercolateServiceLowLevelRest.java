package com.epam.microservice.el.impl;

import com.epam.microservice.el.PercolateService;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

@Service("lowLevelRest")
public class PercolateServiceLowLevelRest implements PercolateService {

    private RestClient restClient;

    private static final String PATH_HITS = "$.hits.total";

    public PercolateServiceLowLevelRest() {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http"));

        // todo add node failure logic here
        builder.setFailureListener(new RestClient.FailureListener() {
            @Override
            public void onFailure(HttpHost host) {
                // todo implement onFailure
            }
        });
        restClient = builder.build();
    }

    public int percolate(String text) throws IOException {

        Map<String, String> params = Collections.emptyMap();
        String jsonString = "{\n"
                + "    \"query\" : {\n"
                + "        \"percolate\" : {\n"
                + "            \"field\" : \"query\",\n"
                + "            \"document\" : {\n"
                + "                \"message\" : \"bonsai tree\"\n"
                + "            }\n"
                + "        }\n"
                + "    }\n"
                + "}";
        HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("POST", "/news_index/_search", params, entity);
        return parseHits(response.getEntity().getContent());
    }

    private int parseHits(InputStream is) throws IOException {
        return JsonPath.parse(is).read(PATH_HITS, Integer.class);
    }

    // todo implement async
    private void callAsync() {
        ResponseListener responseListener = new ResponseListener() {
            @Override
            public void onSuccess(Response response) {

            }

            @Override
            public void onFailure(Exception exception) {

            }
        };
        restClient.performRequestAsync("GET", "/", responseListener);
    }
}
