package top.yxyan.springbootweek04.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.yxyan.springbootweek04.common.Result;
import top.yxyan.springbootweek04.exception.BusinessException;
import top.yxyan.springbootweek04.utils.FileUploadUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/file")
public class UploadController {

    /**
     * 单文件上传
     */
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = FileUploadUtil.upload(file);
            String url = "/upload/" + fileName;
            log.info("文件上传成功，访问路径: {}", url);
            return Result.success(url);
        } catch (BusinessException e) {
            return Result.error(e.getCode(), e.getMessage());
        } catch (IOException e) {
            log.error("文件上传失败", e);
            return Result.error(500, "文件上传失败: " + e.getMessage());
        } catch (Exception e) {
            log.error("系统异常", e);
            return Result.error(500, "系统异常: " + e.getMessage());
        }
    }

    /**
     * 批量文件上传
     */
    @PostMapping("/upload/batch")
    public Result<List<String>> uploadBatch(@RequestParam("files") MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return Result.error(400, "文件不能为空");
        }

        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                continue;
            }
            try {
                String fileName = FileUploadUtil.upload(file);
                urls.add("/upload/" + fileName);
            } catch (BusinessException e) {
                return Result.error(e.getCode(), e.getMessage());
            } catch (IOException e) {
                log.error("批量上传失败", e);
                return Result.error(500, "文件上传失败: " + file.getOriginalFilename());
            } catch (Exception e) {
                log.error("系统异常", e);
                return Result.error(500, "系统异常: " + e.getMessage());
            }
        }

        if (urls.isEmpty()) {
            return Result.error(400, "没有有效的文件可上传");
        }

        return Result.success(urls);
    }
}