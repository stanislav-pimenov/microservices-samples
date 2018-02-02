package com.epam.microservice.el.impl;

import com.epam.microservice.el.PercolateService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.JestResultHandler;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.mapping.PutMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Scanner;

@Service("jest")
public class PercolateServiceJest implements PercolateService {

    private JestClient client;

    Logger logger = LoggerFactory.getLogger(PercolateServiceJest.class);

    public PercolateServiceJest() throws IOException {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://localhost:9200")
                .multiThreaded(true)
                //Per default this implementation will create no more than 2 concurrent connections per given route
                .defaultMaxTotalConnectionPerRoute(10)
                // and no more 20 connections in total
                .maxTotalConnection(10)
                .build());
        client = factory.getObject();
        createMapping();
    }

    @Override
    public int percolate(String text) throws Exception {
        String query = readJsonTemplate("percolateQuery.json");

        Search search = new Search.Builder(query)
                // multiple index or types can be added.
                .addIndex("news_index")
                .build();

        SearchResult result = client.execute(search);
        logger.info("\nStatus: {}\nResponse: {}", result.getResponseCode(), result.getJsonString());
        return Math.toIntExact(result.getTotal());
    }

    private void createMapping() throws IOException {

        // create mapping
        PutMapping putMapping = new PutMapping.Builder(
                "news_index",
                "doc",
                readJsonTemplate("indexMapping.json")
        ).build();

        client.executeAsync(putMapping, new JestResultHandler<JestResult>() {
            @Override
            public void completed(JestResult result) {
                //... do process result ....
                logger.info("\nStatus: {}\nResponse: {}", result.getResponseCode(), result.getJsonString());
            }

            @Override
            public void failed(Exception ex) {
                logger.error("failed", ex);
            }
        });
    }

    private String readJsonTemplate(final String templateName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new Scanner(classLoader.getResourceAsStream("templates/" + templateName), "UTF-8").useDelimiter("\\A").next();
    }
}
