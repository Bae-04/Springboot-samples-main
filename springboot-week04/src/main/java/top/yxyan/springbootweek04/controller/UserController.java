package top.yxyan.springbootweek04.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yxyan.springbootweek04.common.Result;
import top.yxyan.springbootweek04.entity.User;


import java.time.LocalDateTime;


/**
 * @author yxyan
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    /**
     * 获取用户信息,测试消息转换器
     **/
    @GetMapping("/info")
   public Result<User> getUserInfo(){
        User user = new User();
        user.setId(123456789L);
        user.setUsername("yyx");
        user.setCreateTime(LocalDateTime.now());
        return Result.success(user);
    }
}