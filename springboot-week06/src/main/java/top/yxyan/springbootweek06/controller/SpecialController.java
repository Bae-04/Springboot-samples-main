package top.yxyan.springbootweek06.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.yxyan.springbootweek06.common.Result;
import top.yxyan.springbootweek06.entity.Special;
import top.yxyan.springbootweek06.service.SpecialService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/special")
@Tag(name = "专栏接口", description = "专栏接口")
@RequiredArgsConstructor
public class SpecialController {
    private final SpecialService specialService;

    @GetMapping("/page")
    @Operation(summary = "分页查询专栏")
    public Result<Map<String, Object>> selectByTitle(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Page<Special> page = specialService.selectByTitle(title, pageNum, pageSize);

        Map<String, Object> data = new HashMap<>();
        data.put("list", page.getRecords());
        data.put("rows", page.getRecords());
        data.put("records", page.getRecords());
        data.put("total", 100);

        Map<String, Object> wrap = new HashMap<>();
        wrap.put("data", data);
        wrap.put("total", 100);
        wrap.put("rows", page.getRecords());

        return Result.success(wrap);
    }

    // 👇 新增这个接口
    @GetMapping("/detail")
    @Operation(summary = "查询专栏详情")
    public Result<Special> getDetail(@RequestParam Long id) {
        Special special = specialService.getById(id);
        if (special == null) {
            return Result.error("专栏不存在");
        }
        return Result.success(special);
    }
}