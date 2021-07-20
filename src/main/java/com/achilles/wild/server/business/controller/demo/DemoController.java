package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.business.controller.service.DemoService;
import com.achilles.wild.server.common.aop.limit.annotation.IgnoreRateLimit;
import com.achilles.wild.server.common.listener.event.MyApplicationEvent;
import com.achilles.wild.server.model.response.BaseResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
public class DemoController {

    @Autowired
    ApplicationContext applicationContext;

    @Value("#{${test.map:{\"key1\":\"111\",\"key2\":\"222\"}}}")
    private Map<String,String> map;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    HttpServletResponse httpServletResponse;

    @Autowired
    DemoService demoService;

    @GetMapping(path = "/check/heartbeat")
    @IgnoreRateLimit
//    @CommonQpsLimit(permitsPerSecond = 0.2,code = "0",message = "checkHeartBeat too much")
    public String checkHeartBeat(){
        return "Everything is fine !";
    }

    //    @ControllerLog
    @GetMapping(path = "/{id}")
    public Long getConfig(@PathVariable("id") Long id,
                             @RequestParam(name="name",defaultValue = "Achilles") String name,
                             @RequestParam(name="limit",defaultValue = "10") Integer limit,
                             @RequestHeader(name = "traceId",required = false) String traceId){


        return id;
    }

    @GetMapping(path = "/async")
    public BaseResult async() throws ExecutionException, InterruptedException {

        log.info("------async-----");

        demoService.doIt(System.currentTimeMillis() + "");

        Future<String> future = demoService.asyncReturnFuture(System.currentTimeMillis() + "");

        log.info("-----future----get : " +future.get());

        return new BaseResult();
    }

    @GetMapping(path = "/event")
    public String invokeEvent(){

        log.info("----------------------invokeEvent-------------------------------");

        applicationContext.publishEvent(new MyApplicationEvent("invokeEvent"));

        return "invokeEvent ok";
    }

}
