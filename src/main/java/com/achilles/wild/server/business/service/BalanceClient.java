package com.achilles.wild.server.business.service;

import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "github-client", url = "http://localhost:8080/achilles/",fallback = BalanceClient.DefaultFallback.class,
        configuration = GitHubExampleConfig.class)
public interface BalanceClient {

    @GetMapping(value = "/demo/check/heartbeat")
//    @Headers(value = {"Content-Type: application/json","traceId=202104011241111"})
    String heartbeat(@RequestHeader("traceId") String traceId);

    @GetMapping(value = "/demo/check/heartbeat")
    String heartbeat(@RequestHeader("traceId") Map<String, String> headerMap);


    @Component
    public class DefaultFallback implements BalanceClient {
        @Override
        public String heartbeat(String traceId) {
            return "DefaultFallback heartbeat";
        }

        @Override
        public String heartbeat(Map<String, String> headerMap) {
            return "DefaultFallback heartbeat map";
        }
    }
}
