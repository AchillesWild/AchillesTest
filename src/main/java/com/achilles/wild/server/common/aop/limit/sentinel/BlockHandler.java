package com.achilles.wild.server.common.aop.limit.sentinel;

import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockHandler {

    /**
     * 订单查询接口抛出限流或降级时的处理逻辑
     *
     * 注意: 方法参数、返回值要与原函数保持一致
     * @return
     */
    public static String exception(String name, BlockException e) {

        log.info("==================BlockException ============"+name);

        return "exception degrade";
    }

    public static String qps(String name, BlockException e) {

        log.info("==================qps limit ============"+name);

        throw new BizException(BaseResultCode.REQUESTS_TOO_FREQUENT);
    }
}
