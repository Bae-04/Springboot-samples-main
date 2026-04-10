package top.yxyan.springbootweek04.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author yxyan
 * @description 用户实体类
 */
@Data
public class User {
    private Long id;
    private String username;
    private LocalDateTime createTime;
}