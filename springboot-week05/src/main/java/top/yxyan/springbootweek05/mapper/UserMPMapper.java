package top.yxyan.springbootweek05.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.yxyan.springbootweek05.entity.User;

@Mapper
public interface UserMPMapper extends BaseMapper<User> {
}
