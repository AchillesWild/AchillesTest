package com.achilles.wild.server.tool.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceTest {


    /**
     * 定时任务
     *
     * @param args
     */
    public static void main(String[] args) {

//        new AtomicInteger().addAndGet()

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(
                new ThreadFactoryBuilder().setNameFormat("single_pool_worker_%d").build());

        service.scheduleAtFixedRate(()->{
            int i = 1;
            try {
                System.out.println(i+"--------------"+Thread.currentThread().getName());
//                Long.parseLong("");
                i++;
                // i为5时发生by zero异常
                if (i == 5) {
                    int a = 1 / 0;
                }
            } catch (Exception e) {
                System.out.println("发生异常");
            }

        }, 0, 1, TimeUnit.SECONDS);
    }

}
