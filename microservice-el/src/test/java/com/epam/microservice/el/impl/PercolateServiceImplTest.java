package com.epam.microservice.el.impl;

import com.epam.microservice.el.PercolateService;
import com.epam.microservice.el.RestFacade;
import com.epam.microservice.el.dto.Hits;
import com.epam.microservice.el.dto.PercolateResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PercolateServiceImplTest {

    @TestConfiguration
    static class PercolateServiceImplTestContextConfiguration {

        @Bean
        public PercolateService percolateService() {
            return new PercolateServiceImpl();
        }
    }

    @Autowired
    PercolateService percolateService;

    @MockBean
    RestFacade restFacade;

    @Test
    public void shouldReturnTotal() throws Exception {
        // given
        final PercolateResponse response = new PercolateResponse();
        final Hits hits = new Hits();
        hits.setTotal(3L);
        response.setHits(hits);
        when(restFacade.callESWithPost(anyString(), anyObject(), any(Class.class))).thenReturn(response);

        //when
        int total = percolateService.percolate("some text here");

        assertThat(total, is(3));

    }

}
