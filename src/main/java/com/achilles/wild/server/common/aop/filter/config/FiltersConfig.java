package com.achilles.wild.server.common.aop.filter.config;

import com.achilles.wild.server.common.aop.filter.CommonFilter;
import com.achilles.wild.server.common.aop.filter.FirstFilter;
import com.achilles.wild.server.common.aop.filter.StaticFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FiltersConfig {

    @Bean
    public FilterRegistrationBean<FirstFilter> firstFilter() {
        FilterRegistrationBean<FirstFilter>  filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new FirstFilter());
        filterRegistrationBean.setName("firstFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(0);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean staticFilter() {
        FilterRegistrationBean<StaticFilter> filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new StaticFilter());
        filterRegistrationBean.setName("staticFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addInitParameter("swagger_suffix","/swagger-ui/index.html");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean commonFilter() {
        FilterRegistrationBean<CommonFilter> filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new CommonFilter());
        filterRegistrationBean.setName("commonFilter");
        filterRegistrationBean.addUrlPatterns("/demo/*","/flow/*");
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }
}
