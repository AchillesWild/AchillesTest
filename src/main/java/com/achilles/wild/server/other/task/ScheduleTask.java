package com.achilles.wild.server.other.task;

import com.achilles.wild.server.common.constans.CommonConstant;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduleTask {

    private final static Logger log = LoggerFactory.getLogger(ScheduleTask.class);

    //3.添加定时任务
//    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate=20000000)
    private void configureTasks() {
        String traceId = GenerateUniqueUtil.getTraceId("task1");
        MDC.put(CommonConstant.TRACE_ID,traceId);
        log.debug("---------task---------traceId  : " + traceId);

        MDC.remove(CommonConstant.TRACE_ID);
    }

    @Scheduled(fixedRate=20000000)
    private void configureTasks2() throws Exception{
        String traceId = GenerateUniqueUtil.getTraceId("task2");
        MDC.put(CommonConstant.TRACE_ID,traceId);
        log.debug("---------task2---------traceId  : " + traceId);
        Thread.sleep(5000L);
        MDC.remove(CommonConstant.TRACE_ID);
    }
}
