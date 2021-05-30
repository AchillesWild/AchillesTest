package com.achilles.wild.server.tool.thread;

import com.achilles.wild.server.other.thread.DemoThread;
import com.achilles.wild.server.other.thread.RunnableThread;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DemoThreadTest {

    private final static Logger LOG = LoggerFactory.getLogger(DemoThreadTest.class);


    @Test
    public void demoThreadTest() {
        for (int i = 0; i < 10; i++) {
            DemoThread mTh1=new DemoThread(System.currentTimeMillis()+"    @@@@@@    "+i+"  main ");
            mTh1.start();
        }
    }

    @Test
    public void threadTest() {
        new Thread() {
            public void run() {
                System.out.println("匿名内部类创建线程方式1...");
            };
        }.start();
    }


    @Test
    public void runnableThreadTest() {
        for (int i = 0; i < 10; i++) {
            new Thread(new RunnableThread(System.currentTimeMillis()+"    ########    "+i+"  main ")).start();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOG.info("********************************************************");
    }

    @Test
    public void runnableThreadTest2() {
        System.out.println(Thread.currentThread().getName());
        new Thread(()-> System.out.println("匿名内部类创建线程方式2..."+Thread.currentThread().getName())).start();
    }

    @Test
    public void callThreadTest() {
//        CallThread td = new CallThread(9);
//
//        //1.执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。
//        FutureTask<Integer> result = new FutureTask<>(td);
//        new Thread(result).start();
//        //2.接收线程运算后的结果
//        try {
//            Integer sum = result.get();  //FutureTask 可用于 闭锁 类似于CountDownLatch的作用，在所有的线程没有执行完成之后这里是不会执行的
//            System.out.println("------------------------------------"+sum);
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    static ThreadLocal<String> localVar = new ThreadLocal<>();
    static ThreadLocal<String> localVar2 = new ThreadLocal<>();

    @Test
    public void executorThreadTest() {
        //创建带有5个线程的线程池
        //返回的实际上是 ExecutorService,而ExecutorService是Executor的子接口
        localVar2.set(Thread.currentThread().getName());
        Executor threadPool = Executors.newFixedThreadPool(20);
        for(int i = 0 ;i < 10 ; i++) {
            threadPool.execute(()-> {

                localVar.set(Thread.currentThread().getName());
                    //System.out.println(Thread.currentThread().getName()+" is running");
                System.out.println("11111111==="+localVar.get());
                System.out.println("33333333333==="+localVar2.get());
            });
        }
        //Object o =localVar.get();
        //System.out.println();
    }



}
