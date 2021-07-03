package com.achilles.wild.server.common.aop.limit;

import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.aop.limit.annotation.CommonRateLimit;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Method;

//@Aspect
//@Component
//@Order(0)
@Slf4j
public class CommonRateLimitAspect {

        private final static String LOG_PREFIX = "";

    @Value("${open.rate.limit:true}")
    Boolean openRateLimit;

        @Autowired
        CommonRateLimiterConfig commonRateLimiterConfig;

        @Pointcut("@annotation(com.achilles.wild.server.common.aop.limit.annotation.CommonRateLimit)")
        public void commonRateLimit() {}

        /**
         * 在切点之前织入
         * @param joinPoint
         * @throws Throwable
         */
        @Before("commonRateLimit()")
        public void doBefore(JoinPoint joinPoint) throws Throwable {

        }

        /**
         * 环绕
         * @param proceedingJoinPoint
         * @return
         * @throws Throwable
         */
        @Around("commonRateLimit()")
        public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

            if (!openRateLimit) {
                return proceedingJoinPoint.proceed();
            }

            Signature signature = proceedingJoinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature)signature;
            String methodName= methodSignature.getName();

            Method currentMethod = proceedingJoinPoint.getTarget().getClass().getMethod(methodName,methodSignature.getParameterTypes());

            CommonRateLimit annotation = currentMethod.getAnnotation(CommonRateLimit.class);
            Double permitsPerSecond = annotation.permitsPerSecond();
            if (permitsPerSecond == null) {
                throw new BizException(BaseResultCode.ILLEGAL_PARAM.code,BaseResultCode.ILLEGAL_PARAM.message);
            }
            String path = signature.getDeclaringTypeName() + "#" + methodName;
            RateLimiter rateLimiter = commonRateLimiterConfig.getRateLimiter(path,permitsPerSecond);
            if (!rateLimiter.tryAcquire()) {
                if (StringUtils.isNotEmpty(annotation.code()) || StringUtils.isNotEmpty(annotation.message()) ) {
                    throw new BizException(annotation.code(),annotation.message());
                } else {
                    throw new BizException(BaseResultCode.REQUESTS_TOO_FREQUENT.code,BaseResultCode.REQUESTS_TOO_FREQUENT.message);
                }
            }

            return proceedingJoinPoint.proceed();
        }

        /**
         * 在切点之后织入
         * @throws Throwable
         */
        @After("commonRateLimit()")
        public void doAfter() throws Throwable {

        }

        /**
         * 在切点之后织入
         * @throws Throwable
         */
        @AfterThrowing("commonRateLimit()")
        public void afterThrowing() throws Throwable {
//            log.info(LOG_PREFIX+"#-------------------------------afterThrowing---------------------------------------");
        }
}
