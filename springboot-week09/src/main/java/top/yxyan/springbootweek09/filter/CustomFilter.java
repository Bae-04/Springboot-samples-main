package top.yxyan.springbootweek09.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = {"/api/hello"})
public class CustomFilter implements jakarta.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("CustomFilter 过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("CustomFilter 拦截请求,做日志的记录，安全或权限的认证");
        log.info("CustomFilter 放行,访问 /api/hello 接口");
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("接口执行完毕，返回 Customfilter 处理");
    }

    @Override
    public void destroy() {
        log.info("CustomFilter 过滤器销毁");
    }
}
