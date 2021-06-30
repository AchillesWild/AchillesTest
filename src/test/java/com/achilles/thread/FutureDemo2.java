package com.achilles.thread;

import com.achilles.thread.model.RealData;
import com.google.common.util.concurrent.*;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

@Log4j2
public class FutureDemo2 {

    public static void main(String[] args) throws InterruptedException {

        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        ListenableFuture<String> task = service.submit(new RealData("Achilles"));

        Futures.addCallback(task,new FutureCallback<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    log.info("-------success------------------" + task.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                log.info("--------------fail -异常同步返回----------------");
            }
        },MoreExecutors.newDirectExecutorService());

        Thread.sleep(2000l);
        log.info("main -----------------");
    }
}
