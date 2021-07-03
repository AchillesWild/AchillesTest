package com.achilles.thread;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;

@Slf4j
public class ListenableFutureTest {

    static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    public static void main(String[] args) throws InterruptedException {

        ListenableFuture<String> task = service.submit(() -> {
            Thread.sleep(2000l);
            return "Achilles _" + System.currentTimeMillis();
        });

        Futures.addCallback(task,new FutureCallback<String>() {

            @Override
            public void onSuccess(String result) {

                log.info("success result : " + result);
            }

            @Override
            public void onFailure(Throwable e) {
                log.error("onFailure : " +e.getMessage());
            }
        },MoreExecutors.newDirectExecutorService());

//        Thread.sleep(2000l);
        log.info("main -----------------");
    }
}
