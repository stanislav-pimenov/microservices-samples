package com.epam.microservice.el;

import com.epam.microservice.el.dto.PercolateResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@Service
public class RestFacade {

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(RestFacade.class);
       /*
    http://cloud.spring.io/spring-cloud-netflix/single/spring-cloud-netflix.html#_circuit_breaker_hystrix_clients

    A service failure in the lower level of services can cause cascading failure all the way up to the user.
    When calls to a particular service is greater than circuitBreaker.requestVolumeThreshold (default: 20 requests) and
    failue percentage is greater than circuitBreaker.errorThresholdPercentage (default: >50%) in a rolling window defined
    by metrics.rollingStats.timeInMilliseconds (default: 10 seconds), the circuit opens and the call is not made.
    In cases of error and an open circuit a fallback can be provided by the developer.
     */

    @HystrixCommand(defaultFallback = "fallbackMethod",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "51"),
                    @HystrixProperty(name = "circuitBreaker.timeInMilliseconds", value = "10000")
            })
    public <T, R> R callESWithPost(String url, T entity, Class<R> clazz) {
        ResponseEntity<R> percolateResponse = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> httpEntity = new HttpEntity<>(entity, headers);
        try {
            percolateResponse = restTemplate.postForEntity(url, httpEntity, clazz);
        } catch (HttpStatusCodeException ex) {
            logger.error("Status: {}", ex.getStatusCode(), ex);
            throw ex;
        } catch (ResourceAccessException ex) {
            logger.error("Timeout happened", ex);
            throw ex;
        } catch (RestClientException | HttpMessageNotReadableException ex) {
            logger.error("Other Errors", ex);
            throw ex;
        }
        return percolateResponse.getBody();
    }

    public ResponseEntity<PercolateResponse> fallbackMethod() {
        //return callES(entity, EL_URL_SECONDARY);
        throw new RuntimeException("Todo implement custom exception");
    }
}
