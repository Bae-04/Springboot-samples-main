package top.yxyan.springbootweek08.like.ddto;

import jakarta.validation.constraints.NotBlank;

public record LikeToggleRequest(
        @NotBlank(message = "内容ID不能为空")
        String contentId,
        @NotBlank(message = "用户ID不能为空")
        String userId
) {
}
