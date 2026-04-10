package top.yxyan.springbootweek05.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.yxyan.springbootweek05.entity.User;
import top.yxyan.springbootweek05.mapper.UserMPMapper;

@Service
@RequiredArgsConstructor
public class UserMPService {
    private final UserMPMapper userMPMapper;
    public Page<User> page(String username, Integer pageNum, Integer pageSize) {
        // 1. 构造分⻚参数：pageNum（⻚码，从1开始）、pageSize（每⻚条数）
        Page<User> page = Page.of(pageNum, pageSize);
        // 2. 构造条件构造器：LambdaQueryWrapper（类型安全，避免字段名写错）
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        // 条件：⽤户名不为空时，模糊查询（第⼀个参数为条件，满⾜才拼接）
        wrapper.like(username != null && !username.isEmpty(), User::getUsername, username);
        // 3. 调⽤BaseMapper的selectPage⽅法，实现条件分⻚
        return userMPMapper.selectPage(page, wrapper);
    }

    public int add(User user) {
        return userMPMapper.insert(user);
    }

    public int delete(Long id) {
        return userMPMapper.deleteById(id);
    }
}
