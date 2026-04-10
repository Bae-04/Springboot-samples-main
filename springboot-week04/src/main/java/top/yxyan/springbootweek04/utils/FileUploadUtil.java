package top.yxyan.springbootweek04.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import top.yxyan.springbootweek04.exception.BusinessException;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Slf4j
public class FileUploadUtil {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            ".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp",
            ".pdf", ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx",
            ".txt", ".md", ".csv",
            ".zip", ".rar", ".7z",
            ".json", ".xml"
    );

    /**
     * 获取上传目录（target/classes/static/upload）
     */
    private static String getUploadDir() {
        // 获取当前项目的根目录
        String userDir = System.getProperty("user.dir");
        // 构建 target/classes/static/upload 路径
        String uploadDir = userDir + File.separator + "target"
                + File.separator + "classes"
                + File.separator + "static"
                + File.separator + "upload"
                + File.separator;

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                log.info("创建上传目录成功: {}", uploadDir);
            } else {
                log.error("创建上传目录失败: {}", uploadDir);
            }
        }
        return uploadDir;
    }

    public static String upload(MultipartFile file) throws IOException {
        log.info("开始上传文件: {}", file.getOriginalFilename());

        // 参数校验
        if (file == null || file.isEmpty()) {
            throw new BusinessException(400, "文件不能为空");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new BusinessException(400, "文件名不能为空");
        }

        // 获取文件后缀
        int lastDotIndex = originalFilename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new BusinessException(400, "文件缺少扩展名");
        }

        String suffix = originalFilename.substring(lastDotIndex).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(suffix)) {
            throw new BusinessException(400, "不支持的文件类型: " + suffix);
        }

        // 生成唯一文件名（UUID）
        String fileName = UUID.randomUUID() + suffix;
        String uploadDir = getUploadDir();
        File dest = new File(uploadDir + fileName);

        // 保存文件
        file.transferTo(dest);
        log.info("文件上传成功: {}", dest.getAbsolutePath());

        return fileName;
    }
}