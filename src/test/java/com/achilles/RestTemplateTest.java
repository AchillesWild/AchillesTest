package com.achilles;

import com.ConstantService;
import com.RestTemplateBaseService;
import com.achilles.wild.server.model.BaseRequest;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

public class RestTemplateTest implements RestTemplateBaseService, ConstantService {


    String url = urlPrefix+"/demo/check";
    String imageUrl = urlPrefix+"/image/upload";
    String imageUrl2 = urlPrefix+"/image/upload2";

    @Test
    public void exchangeTest(){
        BaseRequest baseRequest =new BaseRequest();
        baseRequest.setId("AchillesWild");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("header131","AchillesWild");
        HttpEntity<String> httpEntity = new HttpEntity<String>(JSONObject.toJSON(baseRequest).toString(), headers);
        ResponseEntity<BaseRequest> response = getRestTemplate().exchange(url, HttpMethod.POST, httpEntity, BaseRequest.class);
        BaseRequest baseRequest1 = response.getBody();

        System.out.println();
    }


    @Test
    public void postForEntityTest(){
        BaseRequest baseRequest =new BaseRequest();
        baseRequest.setId("AchillesWild");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("header131","header-value");
        HttpEntity<String> httpEntity = new HttpEntity<String>(JSONObject.toJSON(baseRequest).toString(), headers);
        ResponseEntity<BaseRequest> response = getRestTemplate().postForEntity(url, httpEntity, BaseRequest.class);
        BaseRequest baseRequest1 = response.getBody();

        System.out.println();
    }

    @Test
    public void postForEntityFileTest() throws Exception{
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("deviceId", "123424");
        String path = "C:\\Users\\Achilles\\Desktop\\test1.jpg";
        FileSystemResource file = new FileSystemResource(path);
        params.add("file", file);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("header131","header-value");
        HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = getRestTemplate().postForEntity(imageUrl, httpEntity, String.class);
        String baseRequest1 = response.getBody();

        System.out.println();
    }

    @Test
    public void postForEntityFileTest2() throws Exception{
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        BaseRequest baseRequest =new BaseRequest();
        baseRequest.setId("AchillesWild");
        params.add("baseRequest", baseRequest);
        Map<String,String> map = new HashMap<>();
        map.put("key1","value11");
        params.add("map", map);
        String path = "C:\\Users\\Achilles\\Desktop\\test1.jpg";
        FileSystemResource file = new FileSystemResource(path);
        params.add("file", file);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("header131","header-value");
        HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = getRestTemplate().postForEntity(imageUrl2, httpEntity, String.class);
        String responseBody = response.getBody();

        System.out.println();
    }

    @Test
    public void postForObjectTest(){
        BaseRequest baseRequest =new BaseRequest();
        baseRequest.setId("AchillesWild");
        BaseRequest result = getRestTemplate().postForObject(url, baseRequest, BaseRequest.class);
        System.out.println();
    }



}
