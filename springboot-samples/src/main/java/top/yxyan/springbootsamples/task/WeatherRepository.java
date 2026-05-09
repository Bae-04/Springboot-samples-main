package top.yxyan.springbootsamples.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 天气数据访问层
 * 继承 JpaRepository 即可获得基本的 CRUD 操作（如 save, findById 等）
 */
@Repository
public interface WeatherRepository extends JpaRepository<WeatherInfo, Long> {

    /**
     * 根据城市名称/ID查询天气信息
     * Spring Data JPA 会自动根据方法名生成 SQL
     * @param city 城市名称/ID
     * @return 天气实体
     */
    WeatherInfo findByCity(String city);
}
