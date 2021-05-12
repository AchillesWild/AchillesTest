package com;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public interface RestTemplateBaseService {


    default RestTemplate getRestTemplate(){

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(30000);
        factory.setConnectTimeout(30000);
        RestTemplate restTemplate = new RestTemplate(factory);

        return restTemplate;
    }
}
