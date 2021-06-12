package com.achilles.wild.server.other.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MultiThreadBase {

    public ExecutorService executor = new ThreadPoolExecutor( 10000, 20000, 10, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100000000), new ThreadPoolExecutor.AbortPolicy());
}
