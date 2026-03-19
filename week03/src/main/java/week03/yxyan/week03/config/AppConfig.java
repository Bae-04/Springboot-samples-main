package week03.yxyan.week03.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

    /**
     * @author yxyan
     * @date 2020/03/03
     * @description AppConfig  单例模式 Singleton
     */
    /**
     * @ConfigurationProperties 批量读取以app开头的配置项
     * @Component
     **/
    @Component
    @ConfigurationProperties(prefix = "app")
    @Data
    public class AppConfig {
        private String appName;
        private String version;
        private String description;
    }
