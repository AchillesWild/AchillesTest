package com.achilles.wild.server.common.aop.interceptor;

import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.aop.limit.annotation.CommonRateLimit;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class RateLimitInterceptor implements HandlerInterceptor {

    private final Map<String, RateLimiter> rateLimiterMap = new HashMap<>();

    private final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Value("${open.rate.limit:true}")
    private Boolean openRateLimit;

    @Value("${limit.rate:1}")
    private Double defaultRateLimit;

    @Value("#{'${rate.limit.filter.exclude-urls}'.split(',')}")
    private List<String> rateLimitFilterExcludeUrls;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!openRateLimit){
            return true;
        }

        String servletPath = request.getServletPath();
        log.debug("--------------------------servletPath : {}",servletPath);

        boolean rateLimitFilterExcludeUrlsMatch = rateLimitFilterExcludeUrls.stream()
                .anyMatch(authFilterExcludeUrl -> PATH_MATCHER.match(authFilterExcludeUrl, servletPath));
        if (rateLimitFilterExcludeUrlsMatch) {
            return true;
        }

        // 404
        if (handler instanceof ResourceHttpRequestHandler) {
            log.debug("----------404--------handler : " + handler);
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;
        String path = method.getBeanType().getName() + "#" + method.getMethod().getName();
        CommonRateLimit annotation = method.getMethodAnnotation(CommonRateLimit.class);
        String key;
        Double rate;
        String code = BaseResultCode.REQUESTS_TOO_FREQUENT.code;
        String message = BaseResultCode.REQUESTS_TOO_FREQUENT.message;
        if (annotation != null){
            double permitsPerSecond = annotation.permitsPerSecond();
            if (permitsPerSecond <= 0) {
                throw new BizException(BaseResultCode.ILLEGAL_PARAM);
            }
            if (StringUtils.isNotBlank(annotation.code())) {
                code = annotation.code();
            }
            if (StringUtils.isNotBlank(annotation.message())) {
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

        return true;
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
