package top.yxyan.springbootweek08.like;

/**
 * 点赞功能在 Redis 中的 key 规则
 */
public final class LikeRedisKey {

    /** 点赞用户集合前缀 */
    public static final String PREFIX_SET = "app:interact:like:users:";
    /** 点赞总数前缀 */
    public static final String PREFIX_COUNT = "app:interact:like:count:";

    private LikeRedisKey() {
    }

    /**
     * 某内容下的点赞用户集合 key
     */
    public static String ofUsers(String contentId) {
        return PREFIX_SET + contentId;
    }

    /**
     * 某内容的点赞总数 key
     */
    public static String ofCount(String contentId) {
        return PREFIX_COUNT + contentId;
    }
}
