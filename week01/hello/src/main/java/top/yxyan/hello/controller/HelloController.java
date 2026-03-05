package top.yxyan.hello.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yxyan.hello.vo.ResultVO;

/**
 * @author 严亚轩
 */
@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public ResultVO<String> hello() {

        return ResultVO.success("Hello Spring Boot 1111");
    }
}
