package top.yxyan.springbootsamples.task;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WeatherService {

    @Resource
    private WeatherRepository weatherRepository;

    @Resource
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 时间格式化器（精确到分钟）
    private static final DateTimeFormatter MINUTE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // ================= 核心配置 =================
    private static final String API_URL = "https://kt564vube3.re.qweatherapi.com/v7/weather/7d";
    private static final String API_KEY = "314e3e10565746628fe618a6c39b4f7d";

    // ================= 2个城市配置 =================
    private static final Map<String, String> CITIES = new HashMap<>();

    static {
        CITIES.put("上海", "101020100");
        CITIES.put("北京", "101010100");
    }

    public void fetchAndSaveWeatherData() {
        log.info("==> 开始拉取 {} 个城市的天气数据...", CITIES.size());

        for (Map.Entry<String, String> city : CITIES.entrySet()) {
            String cityName = city.getKey();
            String locationId = city.getValue();
            fetchCityWeather(cityName, locationId);
        }

        log.info("==> {} 个城市天气数据拉取完成", CITIES.size());
    }

    /**
     * 拉取单个城市的当天天气
     */
    private void fetchCityWeather(String cityName, String locationId) {
        try {
            String requestUrl = API_URL + "?location=" + locationId + "&key=" + API_KEY;
            log.info("==> 拉取 {} 天气数据: {}", cityName, requestUrl);

            String result = restTemplate.getForObject(requestUrl, String.class);

            if (result != null && !result.isEmpty()) {
                JsonNode root = objectMapper.readTree(result);
                String code = root.path("code").asText();

                if ("200".equals(code)) {
                    JsonNode daily = root.path("daily");

                    if (daily.isMissingNode() || daily.size() == 0) {
                        log.error("==> {} API返回数据中没有daily节点", cityName);
                        return;
                    }

                    // 取第一天的数据（今天）
                    JsonNode today = daily.get(0);

                    String fxDate = today.path("fxDate").asText();
                    String tempMax = today.path("tempMax").asText();
                    String tempMin = today.path("tempMin").asText();
                    String textDay = today.path("textDay").asText();
                    String windDirDay = today.path("windDirDay").asText();
                    String humidity = today.path("humidity").asText();

                    // ✅ 使用 LocalDateTime，精确到当前时间（包含时分秒）
                    LocalDateTime now = LocalDateTime.now();

                    // 可选：如果要精确到分钟，可以截断秒和纳秒
                    // LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

                    // 创建新实体
                    WeatherInfo weatherInfo = new WeatherInfo();
                    weatherInfo.setId(null);
                    weatherInfo.setCity(cityName);
                    weatherInfo.setForecastDate(fxDate);
                    weatherInfo.setWeather(textDay);
                    weatherInfo.setTemperature(tempMin + "°C ~ " + tempMax + "°C");
                    weatherInfo.setWindDir(windDirDay);
                    weatherInfo.setHumidity(humidity + "%");
                    weatherInfo.setUpdateTime(now);

                    weatherRepository.save(weatherInfo);

                    // 格式化时间用于日志显示
                    String timeStr = now.format(MINUTE_FORMATTER);
                    log.info("==> {} {} 天气: {} 温度:{}~{}°C 湿度:{}% 风向:{} 更新时间:{}",
                            cityName, fxDate, textDay, tempMin, tempMax, humidity, windDirDay, timeStr);
                } else {
                    log.warn("==> {} API调用失败，状态码: {}", cityName, code);
                }
            }
        } catch (Exception e) {
            log.error("==> 拉取 {} 天气数据失败: {}", cityName, e.getMessage(), e);
        }
    }
}