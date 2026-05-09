package top.yxyan.springbootweek09.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import top.yxyan.springbootweek09.interceptor.AuthInterceptor;
import top.yxyan.springbootweek09.interceptor.LogInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements org.springframework.web.servlet.config.annotation. WebMvcConfigurer {
    private final LogInterceptor logInterceptor;
    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/api/**").order(2);
        registry.addInterceptor(logInterceptor).addPathPatterns("/api/**").order(1);
    }
}
