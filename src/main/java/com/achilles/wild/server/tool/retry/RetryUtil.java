package com.achilles.wild.server.tool.retry;

import java.util.concurrent.TimeUnit;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;
import com.achilles.wild.server.common.listener.RetryLogListener;

/**
 * Retry
 */
public class RetryUtil {

    /**
     * 获取 Retryer
     * @param interval 重试间隔时间
     * @param prefix  日志前缀（公用listener,用于区分日志）
     * @return
     */
    public static Retryer getRetryNeverStop(int interval,String prefix) {
        if(interval==0 || prefix==null || "".equals(prefix.trim()) ){
            return null;
        }
        return RetryerBuilder.<Integer>newBuilder()
            //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
            .retryIfException()
            .retryIfExceptionOfType(Error.class)
            //返回false也需要重试
            //.retryIfResult(Predicates.equalTo(false))
            .retryIfResult(res-> res==0)
            //重调策略
            .withWaitStrategy(WaitStrategies.fixedWait(interval, TimeUnit.SECONDS))
            //一直轮询
            .withStopStrategy(StopStrategies.neverStop())
            .withRetryListener(new RetryLogListener(prefix))
            .build();
    }

    /**
     * 获取 Retryer
     * @param interval 重试间隔时间
     * @param timeOut 每次调用超时时间
     * @param prefix  日志前缀（公用listener,用于区分日志）
     * @return
     */
    public static Retryer getRetryTimeOut(int interval,int timeOut,String prefix) {
        if(interval==0 || prefix==null || "".equals(prefix.trim()) || timeOut==0){
            return null;
        }
        return RetryerBuilder.<Boolean>newBuilder()
            //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
            .retryIfException()
            .retryIfExceptionOfType(Error.class)
            //返回false也需要重试
            .retryIfResult(Predicates.equalTo(false))
            //重调策略
            .withWaitStrategy(WaitStrategies.fixedWait(interval, TimeUnit.SECONDS))
            // 最长允许执行的时间,总的时间重试时间
            .withStopStrategy(StopStrategies.stopAfterDelay(timeOut, TimeUnit.SECONDS))
            //一直轮询
            // .withStopStrategy(StopStrategies.neverStop())
            //监听
            .withRetryListener(new RetryLogListener(prefix))
            .build();
    }


    /**
     * 获取 Retryer
     * @param interval 重试间隔时间
     * @param count   重试次数
     * @param prefix  日志前缀（公用listener,用于区分日志）
     * @return
     */
    public static Retryer getRetryCount(int interval, int count,String prefix) {
        if(interval==0 || count==0 || prefix==null || "".equals(prefix.trim())){
            return null;
        }
        return RetryerBuilder.<Boolean>newBuilder()
            //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
            .retryIfException()
            .retryIfExceptionOfType(Error.class)
            //返回false也需要重试
            .retryIfResult(Predicates.equalTo(false))
            //重调策略
            .withWaitStrategy(WaitStrategies.fixedWait(interval, TimeUnit.SECONDS))
            //尝试次数
            .withStopStrategy(StopStrategies.stopAfterAttempt(count))
            .withRetryListener(new RetryLogListener(prefix))
            .build();
    }
}
