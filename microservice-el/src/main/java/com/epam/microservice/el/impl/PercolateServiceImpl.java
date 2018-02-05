package com.epam.microservice.el.impl;

import com.epam.microservice.el.PercolateService;
import com.epam.microservice.el.RestFacade;
import com.epam.microservice.el.dto.PercolateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Stanislav_Pimenov on 05.02.2018.
 */

@Service("rTemplate")
public class PercolateServiceImpl implements PercolateService {

    // todo move this init into 'DestinationAddress'
    private static final String EL_URL = "http://localhost:9200/news_index/_search";

    private Logger logger = LoggerFactory.getLogger(PercolateServiceImpl.class);

    @Autowired
    private RestFacade restFacade;

    @Override
    public int percolate(String text) throws Exception {
        String jsonString = "{\n"
                + "    \"query\" : {\n"
                + "        \"percolate\" : {\n"
                + "            \"field\" : \"query\",\n"
                + "            \"document\" : {\n"
                + "                \"message\" : \"bonsai tree test\"\n"
                + "            }\n"
                + "        }\n"
                + "    }\n"
                + "}";

        //restTemplate.exchange(EL_URL_PRIMARY, HttpMethod.POST, entity, PercolateResponse.class);

        PercolateResponse percolateResponse = restFacade.callESWithPost(EL_URL, jsonString, PercolateResponse.class);
        return Math.toIntExact(percolateResponse.getHits().getTotal());
    }
}
