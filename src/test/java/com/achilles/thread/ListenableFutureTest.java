package com.achilles.thread;

import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

@Slf4j
public class ListenableFutureTest {

    static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ListenableFuture<String> future = service.submit(() -> {
            log.info("submit ---------------- ");
            Thread.sleep(2000l);
            return GenerateUniqueUtil.getUuId();
        });

        Futures.addCallback(future,new FutureCallback<String>() {

            @Override
            public void onSuccess(String result) {

                log.info("success result : " + result);
            }

            @Override
            public void onFailure(Throwable e) {
                log.error("onFailure : " +e.getMessage());
            }
        },MoreExecutors.newDirectExecutorService());

        ListenableFuture<String> future2 = service.submit(() -> {
            log.info("submit -----2----------- ");
            Thread.sleep(2000l);
            return GenerateUniqueUtil.getUuId();
        });

        Futures.addCallback(future2,new FutureCallback<String>() {

            @Override
            public void onSuccess(String result) {

                log.info("success 2 result  : " + result);
            }

            @Override
            public void onFailure(Throwable e) {
                log.error("onFailure 2 : " +e.getMessage());
            }
        },MoreExecutors.newDirectExecutorService());


        log.info("main -----------------future : " + future.get());
        log.info("main -----------------future2 : " + future2.get());
    }
}
