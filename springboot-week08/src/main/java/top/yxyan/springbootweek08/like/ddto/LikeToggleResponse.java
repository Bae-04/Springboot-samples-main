package top.yxyan.springbootweek08.like.ddto;

public record LikeToggleResponse(
        String contentId,
        boolean liked,
        long totalCount
) {
}
