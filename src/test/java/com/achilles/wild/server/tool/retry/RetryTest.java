package com.achilles.wild.server.tool.retry;

import com.github.rholder.retry.Retryer;

import java.util.concurrent.Callable;

public class RetryTest {

    public static void main(String[] args) {
        uploadOdps("88888888888");
    }

    public static int uploadOdps(final String str) {

//        Retryer<Integer> retry = RetryUtil.getRetryNeverStop(1, "store ");
//
//        try {
//            //重试入口采用call方法，用的是java.util.concurrent.Callable<V>的call方法,所以执行是线程安全的
//            Integer result = retry.call(() -> RetryAddCitizensTask.retryTask("abc"));;
//            System.out.println("=========e=result======="+result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
        return 1;
    }












    public static int uploadOdps2(final String str) {
        //Retryer<Boolean> retry = getRetryCount(1, 3,"入库");
        //Retryer<Boolean> retry = getRetryTimeOut(1, 4,"store ");
        Retryer<Integer> retry = RetryUtil.getRetryNeverStop(1, "store ");

        try {
            //重试入口采用call方法，用的是java.util.concurrent.Callable<V>的call方法,所以执行是线程安全的
            Integer result = retry.call(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    try {
                        //特别注意：返回false说明无需重试，返回true说明需要继续重试
                        System.out.println("=========e=333333=======");
                        return uploadToOdps(str);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });
            System.out.println("=========e=result======="+result);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }



    private static int uploadToOdps(final String str){
        System.out.println("================="+str);
      //  Long.parseLong("rt");
        return 0;
    }


    //public static void main(String[] args) {
    //    final String params = "传入参数,可为任意类型，final修饰即可";
    //    // 重试机制
    //    RetryTemplate oRetryTemplate = new RetryTemplate();
    //    SimpleRetryPolicy oRetryPolicy = new SimpleRetryPolicy();
    //    oRetryPolicy.setMaxAttempts(5);// 重试5次
    //    oRetryTemplate.setRetryPolicy(oRetryPolicy);
    //    try {
    //        // obj为doWithRetry的返回结果，可以为任意类型
    //        Object obj = oRetryTemplate.execute(new RetryCallback<Object, Exception>() {
    //            @Override
    //            public Object doWithRetry(RetryContext context) throws Exception {// 开始重试
    //                System.out.println(params);
    //                testRedo();
    //                return "此处可返回操作结果";
    //            }
    //        }, new RecoveryCallback<Object>() {
    //            @Override
    //            public Object recover(RetryContext context) throws Exception { // 重试多次后都失败了
    //                return null;
    //            }
    //        });
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //}
    //
    //private static void testRedo() {
    //    System.out.println("执行Redo代码");
    //    throw new RuntimeException();
    //}
}
