package top.yxyan.springbootweek02.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 严亚轩
 * @description:Phone
 * @date 2020/4/5 17:09
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor//无参构造
@Builder//参数任意
public class Phone {
    private String band;
    private Double price;
    private String color;
}

