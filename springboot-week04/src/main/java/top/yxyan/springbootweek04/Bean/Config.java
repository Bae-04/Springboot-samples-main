package top.yxyan.springbootweek04.Bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public Student student() {
        Student student = new Student();
        student.setName("yyx");
        return  student;
    }
}
