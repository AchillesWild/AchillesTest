package com.achilles.wild.server.common.aop.filter;

import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.constans.CommonConstant;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.achilles.wild.server.tool.SpringContextUtil;
import com.achilles.wild.server.tool.date.DateUtil;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.env.Environment;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;


public class CommonFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(CommonFilter.class);

    private final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private static final String OPEN_TRACE_ID = "open.trace.id";

    private static final String EXCLUDE_URLS = "auth.filter.exclude-urls";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String servletPath = request.getServletPath();
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Environment environment = SpringContextUtil.getBean(Environment.class);
        String[] authFilterExcludeUrls = environment.getProperty(EXCLUDE_URLS).split(",");

        boolean authFilterExcludeMatch = Arrays.stream(authFilterExcludeUrls)
                .anyMatch(
                        authFilterExcludeUrl ->
                                PATH_MATCHER.match(authFilterExcludeUrl, servletPath) || servletPath.endsWith(authFilterExcludeUrl)
                );
        if (authFilterExcludeMatch) {
            filterChain.doFilter(request, response);
            return;
        }

        long startTime = System.currentTimeMillis();

        String traceId = request.getHeader(CommonConstant.TRACE_ID);
        Boolean openTraceId = environment.getProperty(OPEN_TRACE_ID,Boolean.class) == null ? true : environment.getProperty(OPEN_TRACE_ID,Boolean.class);
        if(openTraceId){
            log.debug("---------------traceId  from  client---------------------:" + traceId);
            checkTraceId(traceId);
        }else{
            traceId = GenerateUniqueUtil.getTraceId(CommonConstant.SYSTEM_CODE);
            log.debug("---------------traceId  generate by  system---------------------:" + traceId);
        }

        log.debug("---------------token---------------------:" + request.getHeader("token"));

        MDC.put(CommonConstant.TRACE_ID,traceId);
        ThreadContext.put(CommonConstant.TRACE_ID, traceId);

        filterChain.doFilter(servletRequest,servletResponse);


        long duration = System.currentTimeMillis() - startTime;
        log.debug(" ----------- time-consuming : "+servletPath+" --> "+duration+"ms");

        MDC.remove(CommonConstant.TRACE_ID);
        ThreadContext.remove(CommonConstant.TRACE_ID);
    }

    @Override
    public void destroy() {
        log.debug("-------------------------------------destroy-----------------------------------");
    }

    private void checkTraceId(String traceId) {
        if(StringUtils.isBlank(traceId)){
            throw new BizException(BaseResultCode.TRACE_ID_NECESSARY.code,BaseResultCode.TRACE_ID_NECESSARY.message);
        }
        if (traceId.length()<20 || traceId.length()>64){
            throw new BizException(BaseResultCode.TRACE_ID_LENGTH_ILLEGAL.code,BaseResultCode.TRACE_ID_LENGTH_ILLEGAL.message);
        }
        String prefix = traceId.substring(0,17);
        Date submitDate = null;
        try {
            submitDate = DateUtil.getDateFormat(DateUtil.YYYY_MM_DD_HH_MM_SS_SSS,prefix);
        } catch (BizException e) {
            throw new BizException(BaseResultCode.TRACE_ID_PREFIX_ILLEGAL.code,BaseResultCode.TRACE_ID_PREFIX_ILLEGAL.message);
        }
        if (submitDate==null){
            throw new BizException(BaseResultCode.TRACE_ID_PREFIX_ILLEGAL.code,BaseResultCode.TRACE_ID_PREFIX_ILLEGAL.message);
        }

        int seconds = DateUtil.getGapSeconds(submitDate);
        if(seconds>300){
            throw new BizException(BaseResultCode.TRACE_ID_CONTENT_EXPIRED.code,BaseResultCode.TRACE_ID_CONTENT_EXPIRED.message);
        }

        if(seconds<-5){
            throw new BizException(BaseResultCode.TRACE_ID_CONTENT_EXCEED_CURRENT.code,BaseResultCode.TRACE_ID_CONTENT_EXCEED_CURRENT.message);
        }
    }
}
