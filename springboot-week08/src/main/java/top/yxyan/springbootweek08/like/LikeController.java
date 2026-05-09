package top.yxyan.springbootweek08.like;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yxyan.springbootweek08.common.Result;
import top.yxyan.springbootweek08.like.ddto.LikeStatusResponse;
import top.yxyan.springbootweek08.like.ddto.LikeToggleRequest;
import top.yxyan.springbootweek08.like.ddto.LikeToggleResponse;

/**
 * 点赞 HTTP 接口
 * <p>切换点赞：POST /api/v1/like/toggle
 * <p>查询状态：GET  /api/v1/like/status
 */
@RestController
@RequestMapping("/api/v1/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/toggle")
    public Result<LikeToggleResponse> toggle(
            @RequestBody @Valid LikeToggleRequest request) {
        return Result.success(likeService.toggle(request.contentId(), request.userId()));
    }

    @GetMapping("/status")
    public ResponseEntity<Result<LikeStatusResponse>> status(
            @RequestParam String contentId,
            @RequestParam String userId) {
        if (contentId == null || contentId.isBlank() || userId == null || userId.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Result.error(400, "contentId与userId不能为空"));
        }
        return ResponseEntity.ok(Result.success(likeService.getStatus(contentId, userId)));
    }
}
