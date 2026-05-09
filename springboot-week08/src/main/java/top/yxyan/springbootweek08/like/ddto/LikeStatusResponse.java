package top.yxyan.springbootweek08.like.ddto;

public record LikeStatusResponse(
        boolean liked,
        long totalCount
) {
}
