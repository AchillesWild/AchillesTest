package com.achilles.wild.server.business.service;

import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "github-client", url = "http://localhost:8080/achilles/",fallback = BalanceClient.DefaultFallback. class,
        configuration = GitHubExampleConfig.class)
public interface BalanceClient {

    @RequestMapping(value = "/check/heartbeat", method = RequestMethod.GET,headers = {"traceId:202104011241111"})
    String heartbeat();

    @Component
    public class DefaultFallback implements BalanceClient {

        @Override
        public String heartbeat() {
            return "DefaultFallback heartbeat";
        }
    }
}
