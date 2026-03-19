package week03.yxyan.week03.controller;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import week03.yxyan.week03.config.AppConfig;

import java.util.Map;

    /**
     * @author yxyan
     * @date 2020/03/19
     * @description 使用@Value注解获取配置文件参数
     */
    @RestController
    @RequestMapping("/config")
    public class BaseConfigController {
        @Value("${server.port}")
        private Integer port;

        @GetMapping("/port")
        public String getPort() {
            return "当前服务器端口号: " + port;
        }

        @Value("${spring.application.name}")
        private String applicationname;
        @Value("${app.app-name}")
        private String appName;
        @Value("${app.version:1.0.0")
        private String appversion;
        @Value("${app.description}")
        private String appdescription;

        @Resource
        private AppConfig appConfig;

        @GetMapping("/info")
        public Map<String, Object> getConfigInfo() {
            return Map.of(
                    "port", port,
                    "applicationname", applicationname,
                    "app-name", appName,
                    "appversion", appversion,
                    "appdescription", appdescription

            );
        }

        @GetMapping("/info2")
        public Map<String, Object> getConfigInfo2() {
            return Map.of(
                    "appName", appConfig.getAppName(),
                    "appVersion", appConfig.getVersion(),
                    "appDescription", appConfig.getDescription()
            );
        }
    }
