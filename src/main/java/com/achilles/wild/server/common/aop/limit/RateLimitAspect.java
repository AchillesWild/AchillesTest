package com.achilles.wild.server.common.aop.limit;

import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.aop.limit.annotation.RateLimit;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(0)
public class RateLimitAspect {

        private final static Logger log = LoggerFactory.getLogger(RateLimitAspect.class);


        @Autowired
        ApplicationContext applicationContext;

        @Pointcut("@annotation(com.achilles.wild.server.common.aop.limit.annotation.RateLimit)")
        public void rateLimit() {}

        @Before("rateLimit()")
        public void doBefore(JoinPoint joinPoint) throws Throwable {
        }

        @Around("rateLimit()")
        public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

            Signature signature = proceedingJoinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature)signature;
            String methodName= methodSignature.getName();
            Method currentMethod = proceedingJoinPoint.getTarget().getClass().getMethod(methodName,methodSignature.getParameterTypes());

            RateLimit annotation = currentMethod.getAnnotation(RateLimit.class);
            BaseRateLimitService rateLimitService = (BaseRateLimitService) applicationContext.getBean(annotation.limitClass());
            RateLimiter rateLimiter = rateLimitService.getRateLimiter();
            if (!rateLimiter.tryAcquire()) {
                throw new BizException(BaseResultCode.REQUESTS_TOO_FREQUENT);
            }

            return proceedingJoinPoint.proceed();
        }

        @After("rateLimit()")
        public void doAfter() throws Throwable {

        }

        @AfterThrowing("rateLimit()")
        public void afterThrowing() throws Throwable {
//            log.info(LOG_PREFIX+"#-------------------------------afterThrowing---------------------------------------");
        }
}
