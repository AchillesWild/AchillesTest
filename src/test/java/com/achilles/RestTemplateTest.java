package com.achilles;

import com.MyApplicationTests;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestTemplateTest extends MyApplicationTests {

    @Autowired
    RestTemplate restTemplate;

    String url = urlPrefix+"/demo/check";

    @Test
    public void test3(){
        BaseRequest baseRequest =new BaseRequest();
        baseRequest.setId("asda");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("header131","headerva131");
        HttpEntity<String> httpEntity = new HttpEntity<String>(JSONObject.toJSON(baseRequest).toString(), headers);

//        String result = restTemplate.postForObject(url, paramMap, String.class);

        ResponseEntity<BaseRequest> response2 = restTemplate.postForEntity(url, httpEntity, BaseRequest.class);
        BaseRequest baseRequest2 = response2.getBody();

        ResponseEntity<BaseRequest> response3 = restTemplate.exchange(url, HttpMethod.POST, httpEntity, BaseRequest.class);
        BaseRequest baseRequest3 = response3.getBody();

        System.out.println();
    }


    @Test
    public void test2(){
        Map<String, Map> paramMap = new HashMap<>();
        Map<String, String> baseRequestMap = new HashMap<>();
        baseRequestMap.put("id","3345");
        paramMap.put("request", baseRequestMap);

//        String result = restTemplate.postForObject(url, paramMap, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("header131","headerva131");
        HttpEntity<Map<String, Map>> httpEntity = new HttpEntity<>(paramMap,headers);
        ResponseEntity<BaseRequest> response2 = restTemplate.postForEntity(url, httpEntity, BaseRequest.class);
        BaseRequest baseRequest2 = response2.getBody();

        ResponseEntity<BaseRequest> response3 = restTemplate.exchange(url, HttpMethod.POST, httpEntity, BaseRequest.class);
        BaseRequest baseRequest3 = response3.getBody();

        System.out.println();
    }

    @Test
    public void test1(){
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("id", "20190225");

//        String result = restTemplate.postForObject(url, paramMap, String.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("header131","headerva131");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap,headers);
        ResponseEntity<String> response2 = restTemplate.postForEntity(url, httpEntity, String.class);

        ResponseEntity<String> response3 = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        System.out.println();
    }

    @Test
    public void test(){
        ResponseEntity<String> response = restTemplate.postForEntity(null,null,String.class);
//        response.getHeaders()

    }
}
