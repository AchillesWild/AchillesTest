package com.achilles.wild.server.common.aop.limit;

import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.aop.limit.annotation.RequestLimit;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
@Order(0)
public class RequestLimitAspect {

        private final static Logger log = LoggerFactory.getLogger(RequestLimitAspect.class);

        private final static String LOG_PREFIX = "";

        private Cache<String, AtomicInteger> integerCache = CacheBuilder.newBuilder().concurrencyLevel(5000).maximumSize(500).expireAfterAccess(10, TimeUnit.SECONDS).build();

        private Cache<String, RateLimiter> rateLimiterCache = CacheBuilder.newBuilder().concurrencyLevel(5000).maximumSize(500).expireAfterAccess(10, TimeUnit.SECONDS).build();

//        private final RateLimiter rateLimiter = RateLimiter.create(1);

        @Value("${request.limit.open:false}")
        private Boolean openRequestLimit;

//        @Value("${request.limit.of.time.consuming.limit}")
//        private Integer time;

//        @Value("${request.limit.of.count.limit.in.time}")
//        private Integer countLimit;

        /** 以 @requestLimit注解为切入点 */
        @Pointcut("@annotation(com.achilles.wild.server.common.aop.limit.annotation.RequestLimit)")
        public void requestLimit() {}

        /**
         * 在切点之前织入
         * @param joinPoint
         * @throws Throwable
         */
        @Before("requestLimit()")
        public void doBefore(JoinPoint joinPoint) throws Throwable {
            if(!openRequestLimit){
                return;
            }
        }

        /**
         * 环绕
         * @param proceedingJoinPoint
         * @return
         * @throws Throwable
         */
        @Around("requestLimit()")
        public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

            if(!openRequestLimit){
                return proceedingJoinPoint.proceed();
            }

            Signature signature = proceedingJoinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature)signature;
            String methodName= methodSignature.getName();
            String path = signature.getDeclaringTypeName()+"#"+methodName;

            Method currentMethod = proceedingJoinPoint.getTarget().getClass().getMethod(methodName,methodSignature.getParameterTypes());
            RequestLimit annotation = currentMethod.getAnnotation(RequestLimit.class);
            double rateLimit = annotation.rateLimit();
            String rateLimiterKey = path+"_RateLimiter";
            RateLimiter rateLimiter;
            synchronized (rateLimiterKey) {
                rateLimiter = rateLimiterCache.getIfPresent(rateLimiterKey);
                log.debug("key : "+rateLimiterKey+"  from cache : "+rateLimiter);
                if (rateLimiter == null){
                    rateLimiter = RateLimiter.create(rateLimit);
                    rateLimiterCache.put(rateLimiterKey,rateLimiter);
                }
            }
            if(!rateLimiter.tryAcquire()){
                throw new BizException(BaseResultCode.REQUESTS_TOO_FREQUENT.code,BaseResultCode.REQUESTS_TOO_FREQUENT.message);
            }

            String countLimitKey = path+"_CountLimit";
            countLimitKey = countLimitKey.intern();
            AtomicInteger atomicInteger;
            synchronized (countLimitKey) {
                atomicInteger = integerCache.getIfPresent(countLimitKey);
                log.debug("key : "+countLimitKey+"  from cache : "+atomicInteger);
                if (atomicInteger == null){
                    atomicInteger = new AtomicInteger();
                    integerCache.put(countLimitKey,atomicInteger);
                }
            }

            atomicInteger.incrementAndGet();
            int count = atomicInteger.get();
            int countLimit = annotation.countLimit();
            if(count>countLimit){
                throw new BizException(BaseResultCode.TOO_MANY_REQUESTS.code,BaseResultCode.TOO_MANY_REQUESTS.message);
            }

            return proceedingJoinPoint.proceed();
        }

        /**
         * 在切点之后织入
         * @throws Throwable
         */
        @After("requestLimit()")
        public void doAfter() throws Throwable {

        }

        /**
         * 在切点之后织入
         * @throws Throwable
         */
        @AfterThrowing("requestLimit()")
        public void afterThrowing() throws Throwable {
//            log.info(LOG_PREFIX+"#-------------------------------afterThrowing---------------------------------------");
        }
}
