package top.yxyan.springbootweek02.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
/**
 * @author 严亚轩
 * @description:Student
 * @date 2020/4/5 17:09
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Student {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthday;//日期
    private Phone phone;
}
