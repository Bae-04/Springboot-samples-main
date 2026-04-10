package top.yxyan.springbootweek05.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import top.yxyan.springbootweek05.entity.User;
import top.yxyan.springbootweek05.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public List<User> search(String username, Integer minAge) {
        return userMapper.selectByCondition(username, minAge);
    }

    public int add(User user) {
        user.setCreateTime(LocalDateTime.now());
        int result = userMapper.insert(user);
//        user.setCreateTime(LocalDateTime.now());
        System.out.println(user);
        return result;
    }

    public User getById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public List<User> list() {
        return userMapper.selectList();
    }

    public int update(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    public int delete(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }
}
