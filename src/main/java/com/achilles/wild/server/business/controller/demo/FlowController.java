package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.common.aop.limit.RateLimitConfig;
import com.achilles.wild.server.common.aop.limit.annotation.CommonRateLimit;
import com.achilles.wild.server.common.aop.limit.annotation.RateLimit;
import com.achilles.wild.server.common.aop.limit.sentinel.BlockHandler;
import com.achilles.wild.server.common.aop.limit.sentinel.FallBackHandler;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/flow", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
public class FlowController {

    @Autowired
    RestTemplate restTemplate;

    String str = "";

    @GetMapping(path = "/limit/aop/{rate}")
    @CommonRateLimit(permitsPerSecond = 0.1,code = "2131",message = "超过了、、、、、")
    public String aopLimit(@PathVariable("rate") Double rate){

        log.info("==================name ============"+rate);

//        RateLimiter rateLimiter = rateLimiterConfig.getInstance(rate);
//        if (!rateLimiter.tryAcquire()) {
//            log.error("=============limit  rate ============"+rate);
//            throw new BizException(BaseResultCode.REQUESTS_TOO_FREQUENT.code,BaseResultCode.REQUESTS_TOO_FREQUENT.message);
//        }


        return "AchillesWild";
    }

    @GetMapping(path = "/limit/{rate}")
    @RateLimit(limitClass = RateLimitConfig.class)
    public String rate(@PathVariable("rate") Double rate){

        log.info("==================name ============"+rate);

//        RateLimiter rateLimiter = rateLimiterConfig.getInstance(rate);
//        if (!rateLimiter.tryAcquire()) {
//            log.error("=============limit  rate ============"+rate);
//            throw new BizException(BaseResultCode.REQUESTS_TOO_FREQUENT.code,BaseResultCode.REQUESTS_TOO_FREQUENT.message);
//        }


        return "AchillesWild";
    }

    @GetMapping(path = "/fallback/{name}")
    @SentinelResource(value = "limit_test",
            fallbackClass = FallBackHandler.class,fallback = "fallback")
    public String fallback(@PathVariable("name") String name){

        log.info("==================name ============"+name);

        Long.parseLong(name);

        try {
            if ("2".equals(name)){
                Thread.sleep(101L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "AchillesWild";
    }

    @GetMapping(path = "/qps/{name}")
    @SentinelResource(value = "com.achilles.wild.server.business.controller.demo.FlowController.qps", blockHandlerClass = BlockHandler.class,blockHandler = "qps")
    public String qps(@PathVariable("name") String name){

        log.info("==================name ============"+name);

        return "AchillesWild";
    }

    @GetMapping(path = "/block/{name}")
    @SentinelResource(value = "limit_test",
            blockHandlerClass = BlockHandler.class,blockHandler = "exception",exceptionsToTrace = IllegalArgumentException.class)
    public String block(@PathVariable("name") String name){

        log.info("==================name ============"+name);

        if (!name.equals("erwe")) {
            throw new IllegalArgumentException();
        }

        return "AchillesWild";
    }
}
