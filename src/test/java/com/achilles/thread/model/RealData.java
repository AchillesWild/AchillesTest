package com.achilles.thread.model;

import java.util.concurrent.Callable;

public class RealData implements Callable<String> {

    private String key;

    public RealData(String key) {
        this.key = key;
    }

    @Override
    public String call() throws Exception {

        Long.parseLong("");

        Thread.sleep(2000l);

        return "test" + key;
    }
}
