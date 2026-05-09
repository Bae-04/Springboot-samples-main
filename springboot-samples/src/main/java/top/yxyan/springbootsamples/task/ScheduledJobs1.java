package top.yxyan.springbootsamples.task;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledJobs1 implements CommandLineRunner {

    @Resource
    private WeatherService weatherService;

    /**
     * fixedRate: 上一次开始执行时间点之后5分钟再执行
     * cron表达式方式: "0
     * 5 * * * ?" 也可以达到每5分钟执行一次的效果
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void weatherDataUpdateJob() {
        log.info("==> 定时任务触发：开始执行天气数据更新...");
        weatherService.fetchAndSaveWeatherData();
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("==> 应用启动，立即执行一次天气数据更新...");
        weatherService.fetchAndSaveWeatherData();
    }
}
