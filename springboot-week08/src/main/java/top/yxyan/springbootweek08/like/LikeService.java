package top.yxyan.springbootweek08.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.yxyan.springbootweek08.like.ddto.LikeStatusResponse;
import top.yxyan.springbootweek08.like.ddto.LikeToggleResponse;
import top.yxyan.springbootweek08.util.RedisUtil;

import java.util.Objects;

/**
 * 点赞服务：使用 Redis Set 记录点赞用户，使用 String 记录点赞总数
 */
@Service
@RequiredArgsConstructor
public class LikeService {

    private final RedisUtil redisUtil;

    /**
     * 切换点赞状态：未赞则赞，已赞则取消
     */
    public LikeToggleResponse toggle(String contentId, String userId) {
        String usersKey = LikeRedisKey.ofUsers(contentId);
        String countKey = LikeRedisKey.ofCount(contentId);

        // 检查是否已点赞
        boolean isLiked = Boolean.TRUE.equals(redisUtil.sIsMember(usersKey, userId));

        if (isLiked) {
            // 取消点赞
            redisUtil.sRemove(usersKey, userId);
            redisUtil.decrement(countKey);
        } else {
            // 点赞
            redisUtil.sAdd(usersKey, userId);
            // 如果 key 不存在，increment 会自动从 0 开始加 1
            redisUtil.increment(countKey);
        }

        long currentCount = getCount(contentId);
        boolean currentState = !isLiked; // 切换后的状态

        return new LikeToggleResponse(contentId, currentState, currentCount);
    }

    /**
     * 获取点赞状态与总数
     */
    public LikeStatusResponse getStatus(String contentId, String userId) {
        String usersKey = LikeRedisKey.ofUsers(contentId);
        boolean isLiked = Boolean.TRUE.equals(redisUtil.sIsMember(usersKey, userId));
        long count = getCount(contentId);
        return new LikeStatusResponse(isLiked, count);
    }

    /**
     * 获取点赞总数
     */
    private long getCount(String contentId) {
        String countKey = LikeRedisKey.ofCount(contentId);
        Object raw = redisUtil.get(countKey);
        if (raw == null) {
            return 0L;
        }
        // FastJson2 反序列化 Integer/Long 统一处理
        return Long.parseLong(Objects.toString(raw, "0"));
    }
}
