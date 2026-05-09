package top.yxyan.springbootweek09.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CORSFilter implements Filter, jakarta.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig){
        log.info("CORSFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type,x-Requested-With");
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())){
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy(){
        log.info("CORSFilter 销毁");
    }
}
