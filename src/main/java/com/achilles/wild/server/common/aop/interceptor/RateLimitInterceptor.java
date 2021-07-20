package com.achilles.wild.server.common.aop.interceptor;

import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.aop.limit.annotation.CommonRateLimit;
import com.achilles.wild.server.common.aop.limit.annotation.IgnoreRateLimit;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
public class RateLimitInterceptor implements HandlerInterceptor {

    private final Map<String, RateLimiter> rateLimiterMap = new HashMap<>();

//    private final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Value("${open.rate.limit:true}")
    Boolean openRateLimit;

    @Value("${default.limit.rate:10000}")
    Double defaultRateLimit;

//    @Value("#{'${rate.limit.filter.exclude-urls:}'.split(',')}")
//    List<String> rateLimitFilterExcludeUrls;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!openRateLimit){
            return true;
        }

        String servletPath = request.getServletPath();
        log.debug("--------------------------servletPath : {}",servletPath);

        if (handler instanceof ResourceHttpRequestHandler) {
            log.debug("----------404--------handler : " + handler);
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;
        IgnoreRateLimit ignoreRateLimit = method.getMethodAnnotation(IgnoreRateLimit.class);
        if (ignoreRateLimit != null) {
            return true;
        }

        rateLimit(method);

        return true;
    }

    private void rateLimit(HandlerMethod method) {

        String path = method.getBeanType().getName() + "#" + method.getMethod().getName();
        String key;
        Double rate;
        String code = BaseResultCode.REQUESTS_TOO_FREQUENT.code;
        String message = BaseResultCode.REQUESTS_TOO_FREQUENT.message;
        CommonRateLimit annotation = method.getMethodAnnotation(CommonRateLimit.class);
        if (annotation != null){
            double permitsPerSecond = annotation.permitsPerSecond();
            if (permitsPerSecond <= 0) {
                throw new BizException(BaseResultCode.ILLEGAL_PARAM);
            }
            if (StringUtils.isNotEmpty(annotation.code())) {
                code = annotation.code();
            }
            if (StringUtils.isNotEmpty(annotation.message())) {
                message = annotation.message();
            }
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
            throw new BizException(code,message);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("-----------postHandle--------status : {}",response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("-----------afterCompletion---------------");
    }

}
