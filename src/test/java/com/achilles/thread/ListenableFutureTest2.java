package com.achilles.thread;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

@Slf4j
public class ListenableFutureTest2 {

    public static void main(String[] args) {

        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        ListenableFuture<String> task = service.submit(() -> {
            Thread.sleep(2000l);
            return "Achilles _" + System.currentTimeMillis();
        });

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
