package top.yxyan.springbootweek08.service;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import top.yxyan.springbootweek08.entity.Address;
import top.yxyan.springbootweek08.entity.User;

import java.util.Objects;

@SpringBootTest
@Slf4j
public class RedisServiceTest {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
//    private StringRedisTemplate stringRedisTemplate;

    /**
     * 测试 StringRedisTemplate 模板基本操作
     */
    @Test
    public void testRedisTemplate() throws Exception{
        //测试字符串操作
//        stringRedisTemplate.opsForValue().set("hello","world");
//        stringRedisTemplate.opsForValue().set("code:18500110011","1234");
//        stringRedisTemplate.opsForValue().set("code:18600110011","4321");
//        redisTemplate.opsForValue().set("code:13311001100","1122");
//        String code = Objects.requireNonNull(redisTemplate.opsForValue().get("code:13311001100")).toString();
//        log.info("13311001100 验证码结果：{}",code);
        //取值
//        String value = stringRedisTemplate.opsForValue().get("hello");
//        log.info("Redis 字符串测试结果：{}",value);
//        String code = stringRedisTemplate.opsForValue().get("code:18500110011");
//        log.info("18500110011 验证码测试结果：{}",code);
//        String code2 = stringRedisTemplate.opsForValue().get("code:18600110011");
//        log.info("18600110011 验证码测试结果：{}",code2);
        //测试对象操作
        Address address = new Address();
        address.setCity("淮安市");
        address.setStreet("洪泽区幸福小区");
        address.setZipCode("212234");

        User user = new User();
        user.setName("小明");
        user.setAge(18);
        user.setEmail("xiaoming@qq.com");
        user.setAddress(address);
        redisTemplate.opsForValue().set("user:001",user);



//        Object userObj = redisTemplate.opsForValue().get("user:001");
//        //反序列化
//        User user2 = JSON.parseObject(JSON.toJSONString(userObj),User.class);
//        log.info("user:001 测试结果：{}",user2);

    }
}
