package com.achilles.wild.server.common.aop.filter;

import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.achilles.wild.server.tool.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class StaticFilter implements Filter {

    private String swaggerSuffix;

    @Override
    public void init(FilterConfig filterConfig) {

        swaggerSuffix = filterConfig.getInitParameter("swagger_suffix");
        log.debug("-------------------------------------init-----------------------------------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String servletPath = request.getServletPath();
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (servletPath.contains(swaggerSuffix)) {
            String key = request.getParameter("key");
            if (!"AchillesTest".equals(key)) {
                String userJson = JsonUtil.toJson(BaseResultCode.PERMISSION_DENIED.message);
                OutputStream out = response.getOutputStream();
                out.write(userJson.getBytes("UTF-8"));
                out.flush();
                return;
            }
        }
        filterChain.doFilter(request, response);
        return;
    }

    @Override
    public void destroy() {
        log.debug("-------------------------------------destroy-----------------------------------");
    }
}
