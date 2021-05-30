package com.achilles.wild.server.other.thread;

public class DemoThread extends Thread{

    private String name;

    public DemoThread(String name){
        this.name=name;
    }

    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println(name + "  RUN  :  " + i);
            /*try {
                sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }
}
