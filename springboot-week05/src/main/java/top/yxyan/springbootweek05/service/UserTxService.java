package top.yxyan.springbootweek05.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yxyan.springbootweek05.entity.User;
import top.yxyan.springbootweek05.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserTxService {
    private final UserMapper userMapper;
    /**
     * 新增两个⽤户，添加⼯⼚事务：⽅法内任意⼀步出错，全部回滚
     *
     * @param user1 ⽤户1
     * @param user2 ⽤户2
     */
    @Transactional
    public void addTwoUsers(User user1, User user2) {
        // 第⼀步：新增⽤户 1
        if (user1 == null) {
            throw new RuntimeException("⽤户 1 不能为空");
        }
        if (user1.getUsername() == null || user1.getUsername().isEmpty()) {
            throw new RuntimeException("⽤户 1 姓名不能为空");
        }
        userMapper.insert(user1);
        // 模拟异常：如果⽤户 2 的⽤户名为空，抛出运⾏时异常，触发事务回滚
        if (user2 == null) {
            throw new RuntimeException("⽤户 2 不能为空");
        }
        if (user2.getUsername() == null || user2.getUsername().isEmpty())
        {
            throw new RuntimeException("⽤户 2 姓名不能为空，事务回滚");
        }
        // 第⼆步：新增⽤户 2（若上⾯抛出异常，此步骤不会执⾏，⽤户 1 也会被回滚）
        userMapper.insert(user2);
    }


}
