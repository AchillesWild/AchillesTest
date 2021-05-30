package com.achilles.wild.server.other.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnableThread implements Runnable{

    private final static Logger LOG = LoggerFactory.getLogger(RunnableThread.class);

    private String name;

    public RunnableThread(String name){
        this.name=name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 13; i++) {
            LOG.info(name + "  RUN  :  " + i);
            //try {
            //    Thread.sleep(300);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }
    }
}
