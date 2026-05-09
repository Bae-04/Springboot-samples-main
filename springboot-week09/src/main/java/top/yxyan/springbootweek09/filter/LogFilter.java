package top.yxyan.springbootweek09.filter;

import jakarta.servlet.*;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LogFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        String clientIp = httpRequest.getRemoteAddr();
        LocalDateTime timestamp = LocalDateTime.now();
        log.info("请求已到达日志过滤器：: Path={},IP{},Time={}", path, clientIp, timestamp);
        chain.doFilter(request, response);
        log.info("接口执行完毕，返回 LogFilter 处理, Time={}",LocalDateTime.now());
    }
}
