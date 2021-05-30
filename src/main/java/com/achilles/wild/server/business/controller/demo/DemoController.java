package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.common.aop.limit.annotation.CommonQpsLimit;
import com.achilles.wild.server.common.aop.log.annotation.IgnoreParams;
import com.achilles.wild.server.model.request.BaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_VALUE)
public class DemoController {

    private final static Logger log = LoggerFactory.getLogger(DemoController.class);


    @Autowired
    ApplicationContext applicationContext;

    @Value("#{${test.map:{\"key1\":\"111\",\"key2\":\"222\"}}}")
    private Map<String,String> map;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    HttpServletResponse httpServletResponse;

    @GetMapping(path = "/check/heartbeat")
    @CommonQpsLimit(permitsPerSecond = 0.2,code = "0",message = "checkHeartBeat too much")
    public String checkHeartBeat(){
        return "Everything is fine !";
    }

    @PostMapping(path = "/check1")
    @IgnoreParams
    public BaseRequest check1(@RequestBody BaseRequest request,HttpServletResponse httpServletResponse){

        String header = httpServletRequest.getHeader("header131");
        httpServletResponse.setHeader("header2222","headerva3333");
        return request;
    }


    @PostMapping(path = "/check")
    @IgnoreParams
    public BaseRequest check(@RequestBody BaseRequest request,HttpServletResponse httpServletResponse){

        String header = httpServletRequest.getHeader("header131");
        httpServletResponse.setHeader("header2222","AchillesWild");
        return request;
    }


    //    @ControllerLog
    @GetMapping(path = "/{id}")
    public Long getConfig(@PathVariable("id") Long id,
                             @RequestParam(name="name",defaultValue = "Achilles") String name,
                             @RequestParam(name="limit",defaultValue = "10") Integer limit,
                             @RequestHeader(name = "traceId",required = false) String traceId){


        return id;
    }

    @GetMapping(path = "/get/{name}")
    public String getName(@PathVariable("name") String name){

        Long.parseLong(name);

        return "AchillesWild";
    }

    @GetMapping(path = "/event")
    public String invokeEvent(){


        return "invokeEvent ok";
    }

}
