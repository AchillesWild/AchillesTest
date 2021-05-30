package com.achilles.wild.server.common.listener;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class RetryLogListener implements RetryListener {

    private final static Logger LOG = LoggerFactory.getLogger(RetryLogListener.class);


    private String prefix;

    public RetryLogListener(String prefix){
        this.prefix=prefix;
    }

    @Override
    public void onRetry(Attempt attempt) {
        // 第几次重试,(注意:第一次重试其实是第一次调用)
        LOG.info(prefix+"[retry]time=" + attempt.getAttemptNumber());

        // 距离第一次重试的延迟
        LOG.info(",delay=" + attempt.getDelaySinceFirstAttempt());

        // 重试结果: 是异常终止, 还是正常返回
        LOG.info(",hasException=" + attempt.hasException());
        LOG.info(",hasResult=" + attempt.hasResult());

        // 是什么原因导致异常
        if (attempt.hasException()) {
            LOG.info(",causeBy=" + attempt.getExceptionCause().toString());
        } else {
            // 正常返回时的结果
            LOG.info(",SUCCESS result=" + attempt.getResult());
        }

        // bad practice: 增加了额外的异常处理代码
        try {
            Object result = attempt.get();
            LOG.info(",result get=" + result);
        } catch (ExecutionException e) {
            LOG.info("this attempt produce exception." + e.getCause().toString());
        }

    }
}
