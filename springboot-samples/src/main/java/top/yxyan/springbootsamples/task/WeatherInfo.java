package top.yxyan.springbootsamples.task;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "weather_info")
public class WeatherInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", length = 50)
    private String city;              // 城市名称

    @Column(name = "forecast_date", length = 20)
    private String forecastDate;      // 预报日期（yyyy-MM-dd）

    @Column(name = "weather", length = 100)
    private String weather;           // 天气现象

    @Column(name = "temperature", length = 50)
    private String temperature;       // 温度范围

    @Column(name = "wind_dir", length = 50)
    private String windDir;           // 风向

    @Column(name = "humidity", length = 10)
    private String humidity;          // 湿度

    // ✅ 方案1：使用 LocalDateTime（推荐，精确到纳秒）
    @Column(name = "update_time", columnDefinition = "DATETIME(0)")
    private LocalDateTime updateTime; // 数据拉取时间（精确到秒）

    // ✅ 方案2：如果要精确到分钟，可以用这个格式
    // @Column(name = "update_time")
    // private LocalDateTime updateTime;
}