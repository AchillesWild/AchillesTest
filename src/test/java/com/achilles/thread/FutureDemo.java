package com.achilles.thread;

import com.achilles.thread.model.RealData;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

@Log4j2
public class FutureDemo {

    public static void main(String[] args) {

        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        ListenableFuture<String> task = service.submit(new RealData("Achilles"));
        task.addListener(() -> {
            try {
                log.info("-------sub------------------" + task.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        },MoreExecutors.directExecutor());

        log.info("main -----------------");
    }
}
