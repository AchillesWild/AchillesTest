package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.common.aop.limit.annotation.CommonQpsLimit;
import com.achilles.wild.server.common.listener.event.MyApplicationEvent;
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

    //    @ControllerLog
    @GetMapping(path = "/{id}")
    public Long getConfig(@PathVariable("id") Long id,
                             @RequestParam(name="name",defaultValue = "Achilles") String name,
                             @RequestParam(name="limit",defaultValue = "10") Integer limit,
                             @RequestHeader(name = "traceId",required = false) String traceId){


        return id;
    }

    @GetMapping(path = "/event")
    public String invokeEvent(){

        applicationContext.publishEvent(new MyApplicationEvent("invokeEvent"));

        return "invokeEvent ok";
    }

}
