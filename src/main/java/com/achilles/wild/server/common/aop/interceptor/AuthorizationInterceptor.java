package com.achilles.wild.server.common.aop.interceptor;

import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.aop.limit.annotation.CommonRateLimit;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final Map<String, RateLimiter> rateLimiterMap = new HashMap<>();

    @Value("${open.rate.limit:true}")
    private Boolean openRateLimit;

    @Value("${limit.rate:1}")
    private Double defaultRateLimit;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!openRateLimit){
            return true;
        }

        String servletPath = request.getServletPath();
        log.debug("--------------------------servletPath : {}",servletPath);

        // 404
        if (handler instanceof ResourceHttpRequestHandler) {
            log.debug("----------404--------handler : " + handler);
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;
        String path = method.getBeanType().getName() + "#" + method.getMethod().getName();
        CommonRateLimit commonRateLimit = method.getMethodAnnotation(CommonRateLimit.class);
        String key;
        Double rate;
        if (commonRateLimit != null){
            double permitsPerSecond = commonRateLimit.permitsPerSecond();
            key = path + "_" + permitsPerSecond;
            rate = permitsPerSecond;
        } else {
            key = path + "_" + defaultRateLimit;
            rate = defaultRateLimit;
        }
        RateLimiter rateLimiter = rateLimiterMap.get(key);
        if (rateLimiter == null) {
            synchronized (rateLimiterMap) {
                rateLimiter = rateLimiterMap.get(key);
                if (rateLimiter == null) {
                    rateLimiter = RateLimiter.create(rate);
                    rateLimiterMap.put(key,rateLimiter);
                }
            }
        }
        if (!rateLimiter.tryAcquire()) {
            throw new BizException(BaseResultCode.REQUESTS_TOO_FREQUENT.code,BaseResultCode.REQUESTS_TOO_FREQUENT.message);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        int status = response.getStatus();
        log.debug("-------------------------status : {}",status);


//        if(status == 404){
//            modelAndView.setViewName("/error/404");
//        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
