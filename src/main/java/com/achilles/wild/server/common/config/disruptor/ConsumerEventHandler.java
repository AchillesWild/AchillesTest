package com.achilles.wild.server.common.config.disruptor;

import com.achilles.wild.server.model.request.BaseRequest;
import com.achilles.wild.server.tool.json.JsonUtil;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 消费者
 */
public class ConsumerEventHandler implements EventHandler<BaseRequest>, InitializingBean,DisposableBean {

    private final static Logger log = LoggerFactory.getLogger(ConsumerEventHandler.class);


    @Override
    public void onEvent(BaseRequest logTimeInfo, long sequence, boolean endOfBatch) {

//        if (logTimeInfo!=null){
//            return;
//        }

        log.debug("disruptor consumer ----- sequence:"+sequence+",endOfBatch:"+endOfBatch+",event:"+ JsonUtil.toJsonString(logTimeInfo));
//        log.debug("disruptor getObjectSize:"+ BeanUtil.getObjectSize(logTimeInfo));

//        try {
//            Thread.sleep(5000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }


    @Override
    public void destroy() throws Exception {
        log.info("-----destroy------");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("-----afterPropertiesSet------");
    }
}
