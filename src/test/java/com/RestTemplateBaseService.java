package com;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public interface RestTemplateBaseService {


    default RestTemplate getRestTemplate(){

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(factory);

        return restTemplate;
    }
}
