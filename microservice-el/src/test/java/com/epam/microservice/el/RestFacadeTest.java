package com.epam.microservice.el;

import com.epam.microservice.el.dto.PercolateResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(value = RestFacade.class)
public class RestFacadeTest {

    @Autowired
    private MockRestServiceServer esServer;

    @Autowired
    RestFacade restFacade;

    private static final String SUCCESS_RESPONSE = "{\n"
            + "    \"took\": 54,\n"
            + "    \"timed_out\": false,\n"
            + "    \"hits\": {\n"
            + "        \"total\": 3,\n"
            + "        \"max_score\": 0.5753642\n"
            + "    }\n"
            + "}";

    @Test
    public void shouldPercolate()
            throws Exception {
        final String esURL = "http://localhost:9200/news_index/_search";
        this.esServer.expect(requestTo(esURL))
                     .andRespond(withSuccess(SUCCESS_RESPONSE, MediaType.APPLICATION_JSON_UTF8));
        String postEntity = "bonsai tree olala";
        PercolateResponse response = restFacade.callESWithPost(esURL, postEntity, PercolateResponse.class);
        assertThat(response.getHits().getTotal(), is(3L));
    }
}
