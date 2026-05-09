package top.yxyan.springbootsamples.task;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.yxyan.springbootsamples.mail.MailService;

import java.text.SimpleDateFormat;
import java.util.Date;

//@Component
//@Slf4j
//public class ScheduledJobs2 {
//    @Resource
//    private MailService mailService;
//
//    @Scheduled(cron = "0 11 17 7 5 ?")
//    public void birthdayEmailJob(){
//        int currentYear = java.time.LocalDate.now().getYear();
//        if (currentYear != 2026) {
//            log.info("当前年份是{}，不是2026年，跳过执行", currentYear);
//            return;
//        }
//        try {
//            String to = "16422802@qq.com";
//            String subject = "生日快乐！";
//            String content = buildBirthdayEmailContent();
//
//            mailService.sendHtmlMail(to, subject, content);
//            log.info("🎂 生日祝福邮件已发送到: {}", to);
//            log.info("发件人信息: 软件2531-2505223115-严亚轩");
//        } catch (Exception e) {
//            log.error("生日祝福邮件发送失败", e);
//        }
//    }
//
//    // 构建生日祝福邮件的HTML内容
//    private String buildBirthdayEmailContent() {
//        return "<!DOCTYPE html>\n" +
//                "<html lang=\"zh-CN\">\n" +
//                "<head>\n" +
//                "    <meta charset=\"UTF-8\">\n" +
//                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
//                "    <title>生日快乐</title>\n" +
//                "</head>\n" +
//                "<body style=\"margin: 0; padding: 0; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif; background-color: #f5f5f5; line-height: 1.5;\">\n" +
//                "    <div style=\"max-width: 520px; margin: 0 auto; padding: 20px;\">\n" +
//                "        <div style=\"background-color: #ffffff; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); overflow: hidden; text-align: center; padding: 40px 32px 48px;\">\n" +
//                "            <h1 style=\"font-size: 28px; font-weight: 600; color: #1a1a1a; margin: 0 0 8px 0;\">🎂 祝你生日快乐！🎂</h1>\n" +
//                "            <div style=\"width: 60px; height: 4px; background-color: #ff6b6b; margin: 20px auto 24px auto; border-radius: 2px;\"></div>\n" +
//                "            <div style=\"text-align: left; color: #4a4a4a; font-size: 15px;\">\n" +
//                "                <p style=\"margin: 0 0 16px 0; font-size: 18px; color: #ff6b6b; font-weight: 600;\">亲爱的朋友：</p>\n" +
//                "                <p style=\"margin: 0 0 20px 0; line-height: 1.8; font-size: 16px;\">\n" +
//                "                    在这个特别的日子里，送上最真挚的祝福：\n" +
//                "                </p>\n" +
//                "                <div style=\"background-color: #fff5f5; padding: 20px; border-radius: 8px; margin: 20px 0;\">\n" +
//                "                    <p style=\"margin: 10px 0; color: #333; font-size: 16px;\">✨ 身体健康，万事如意</p>\n" +
//                "                    <p style=\"margin: 10px 0; color: #333; font-size: 16px;\">✨ 工作顺利，事业有成</p>\n" +
//                "                    <p style=\"margin: 10px 0; color: #333; font-size: 16px;\">✨ 天天开心，幸福安康</p>\n" +
//                "                    <p style=\"margin: 10px 0; color: #333; font-size: 16px;\">✨ 岁岁平安，年年有余</p>\n" +
//                "                </div>\n" +
//                "                <p style=\"margin: 20px 0 0 0; line-height: 1.8; font-size: 16px; color: #666;\">\n" +
//                "                    愿你新的一岁充满阳光与欢笑，所有的美好都如期而至！\n" +
//                "                </p>\n" +
//                "            </div>\n" +
//                "            <div style=\"border-top: 1px solid #eeeeee; padding-top: 24px; margin-top: 32px;\">\n" +
//                "                <p style=\"font-size: 14px; color: #999; margin: 0 0 8px 0; font-weight: 600;\">\n" +
//                "                    此致\n" +
//                "                </p>\n" +
//                "                <p style=\"font-size: 14px; color: #666; margin: 0; font-weight: 500;\">\n" +
//                "                    软件2531-2505223115-严亚轩\n" +
//                "                </p>\n" +
//                "                <p style=\"font-size: 12px; color: #bbb; margin: 8px 0 0 0;\">\n" +
//                "                    " + new SimpleDateFormat("yyyy年MM月dd日").format(new Date()) + "\n" +
//                "                </p>\n" +
//                "            </div>\n" +
//                "        </div>\n" +
//                "        <div style=\"text-align: center; font-size: 12px; color: #a0a0a0; margin-top: 24px;\">\n" +
//                "            🎉 本邮件由系统自动发送，承载满满的祝福 🎉\n" +
//                "        </div>\n" +
//                "    </div>\n" +
//                "</body>\n" +
//                "</html>";
//    }
//}
