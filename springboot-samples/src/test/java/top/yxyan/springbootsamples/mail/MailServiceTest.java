package top.yxyan.springbootsamples.mail;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailServiceTest {
    @Resource
    MailService mailService;

    @Test
    public void sendSimpleMail(){
        mailService.sendSimpleMail("1492127902@qq.com",
                "普通文本邮件",
                "普通文本邮件内容测试");
    }

    // HTML格式激活邮件测试
    @Test
    public void sendHtmlMail() throws MessagingException {
        String to = "1492127902@qq.com";
        String subject = "欢迎注册，请激活您的账号";

        // 构建HTML邮件内容（生成图片中的效果）
        String content = buildActivationEmailContent("xxxx");

        mailService.sendHtmlMail(to, subject, content);
        System.out.println("激活邮件发送成功，请检查邮箱：" + to);
    }

    // 构建激活邮件的HTML内容
    private String buildActivationEmailContent(String token) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>账号激活邮件</title>\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif; background-color: #f5f5f5; line-height: 1.5;\">\n" +
                "    <div style=\"max-width: 520px; margin: 0 auto; padding: 20px;\">\n" +
                "        <div style=\"background-color: #ffffff; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); overflow: hidden; text-align: center; padding: 40px 32px 48px;\">\n" +
                "            <h1 style=\"font-size: 24px; font-weight: 600; color: #1a1a1a; margin: 0 0 8px 0; letter-spacing: -0.3px;\">欢迎注册，请激活您的账号</h1>\n" +
                "            <div style=\"width: 40px; height: 3px; background-color: #4f46e5; margin: 16px auto 24px auto; border-radius: 2px;\"></div>\n" +
                "            <div style=\"text-align: left; color: #4a4a4a; font-size: 15px;\">\n" +
                "                <p style=\"margin: 0 0 16px 0;\"><strong>尊敬的用户：</strong></p>\n" +
                "                <p style=\"margin: 0 0 20px 0; line-height: 1.6;\">\n" +
                "                    您好！感谢您注册本站账号，请点击下方按钮完成激活。激活链接 <strong style=\"color: #dc2626;\">24小时内有效</strong>，过期需重新发送邮件。\n" +
                "                </p>\n" +
                "            </div>\n" +
                "            <div style=\"margin: 28px 0 32px 0;\">\n" +
                "                <a href=\"http://localhost:8080/activate?token=" + token + "\" style=\"display: inline-block; background-color: #4f46e5; color: #ffffff; text-decoration: none; font-weight: 500; font-size: 16px; padding: 12px 28px; border-radius: 30px; box-shadow: 0 2px 6px rgba(79,70,229,0.25);\">立即激活账号</a>\n" +
                "            </div>\n" +
                "            <div style=\"border-top: 1px solid #eeeeee; padding-top: 24px; margin-top: 8px;\">\n" +
                "                <p style=\"font-size: 13px; color: #8a8a8a; margin: 0 0 8px 0;\">\n" +
                "                    若按钮无法点击，请复制链接浏览器打开：\n" +
                "                </p>\n" +
                "                <p style=\"font-size: 13px; color: #4f46e5; word-break: break-all; margin: 0; background-color: #f8f9fc; padding: 10px 12px; border-radius: 8px; font-family: monospace;\">\n" +
                "                    http://localhost:8080/activate?token=" + token + "\n" +
                "                </p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div style=\"text-align: center; font-size: 12px; color: #a0a0a0; margin-top: 24px;\">\n" +
                "            本邮件由系统自动发送，请勿直接回复\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
//    @Test
//    void sendAttachmentsMail() throws MessagingException{
//        String filePath1 = "D:\\我的\\照片\\E391B079A4A13F1DEBE193305D8EE6BE.png";
//        // Deleted:String filePath2 = "D:\\我的\\照片\\1E7EE5C2A0921AB02B5FA990E5FBFE80.png";
//        mailService.sendAttachmentsMail("1492127902@qq.com",
//                "这是一封带附件的邮件来自yyx",
//                "邮件中有附件，请注意查收！",
//                filePath1);
//    }

    @Test
    void sendAttachmentsMail() throws MessagingException{
        String filePath1 = "D:\\我的\\照片\\E391B079A4A13F1DEBE193305D8EE6BE.png";
        String filePath2 = "D:\\我的\\照片\\1E7EE5C2A0921AB02B5FA990E5FBFE80.png";
        mailService.sendAttachmentsMail("1492127902@qq.com",
                "这是一封带附件的邮件来自yyx",
                "邮件中有附件，请注意查收！",
                filePath1, filePath2);
    }

}
