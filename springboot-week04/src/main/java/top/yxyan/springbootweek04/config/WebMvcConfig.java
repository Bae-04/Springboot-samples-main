package top.yxyan.springbootweek04.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public MappingJackson2HttpMessageConverter customJacksonConverter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(new JavaTimeModule().addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter)),
                        new SimpleModule().addSerializer(Long.class, ToStringSerializer.instance))
                .build();
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置访问 /upload/** 映射到 target/classes/static/upload 目录
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("classpath:/static/upload/");
    }
}