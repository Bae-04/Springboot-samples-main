package top.yxyan.springbootweek09.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import top.yxyan.springbootweek09.filter.AuthFilter;
import top.yxyan.springbootweek09.filter.CORSFilter;
import top.yxyan.springbootweek09.filter.CustomFilter;
import top.yxyan.springbootweek09.filter.LogFilter;

//@Configuration
public class FilterConfig {
    //自定义过滤器
    @Bean
    public FilterRegistrationBean<CustomFilter> customFilterFilterRegistrationBean() {
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomFilter());
        registrationBean.addUrlPatterns("/api/hello");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    //日志过滤器
    @Bean
    public FilterRegistrationBean<LogFilter> logFilterFilterRegistrationBean() {
        FilterRegistrationBean<LogFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LogFilter());
        registrationBean.addUrlPatterns("/api/hello");
        registrationBean.setOrder(2);
        return registrationBean;
    }

    //认证过滤器
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterFilterRegistrationBean() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter());
        registrationBean.addUrlPatterns("/api/hello");
        registrationBean.setOrder(3);
        return registrationBean;
    }

    //跨域过滤器
    @Bean
    public FilterRegistrationBean<CORSFilter> corsFilterFilterRegistrationBean() {
        FilterRegistrationBean<CORSFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CORSFilter());
        registrationBean.addUrlPatterns("/api/hello");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
