package top.yxyan.springbootweek05.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.yxyan.springbootweek05.common.Result;
import top.yxyan.springbootweek05.entity.User;
import top.yxyan.springbootweek05.service.UserMPService;

@RestController
@RequestMapping("/api/user/mp")
@RequiredArgsConstructor
public class UserMPController {
    private final UserMPService userMPService;
    /**
     * 条件分⻚查询接⼝，分⻚参数默认：第1⻚，每⻚10条
     *
     * @param pageNum ⻚码
     * @param pageSize 每⻚数量
     * @param username ⽤户名
     * @return 分⻚数据
     */
    @GetMapping("/page")
    public Result<Page<User>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username) {
        return Result.success(userMPService.page(username, pageNum, pageSize));
    }
    /**
     * 新增接⼝（⽆SQL）
     *
     * @param user 新增数据
     * @return 新增结果
     */
    @PostMapping
    public Result<String> add(@RequestBody User user) {
        int row = userMPService.add(user);
        if (row <= 0) {
            return Result.error("MP 新增失败");
        }
        return Result.success("MP 新增成功");
    }
    /**
     * 删除接⼝（⽆SQL）
     *
     * @param id 删除id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        int row = userMPService.delete(id);
        if (row <= 0) {
            return Result.error("MP 删除失败");
        }
        return Result.success("MP 删除成功");
    }
}
