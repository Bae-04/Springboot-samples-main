package top.yxyan.springbootsamples.mail;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailService {
    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendSimpleMail(String to,String subject,String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }


    public void sendHtmlMail(String to,String subject,String content) throws
        MessagingException {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            mailSender.send(message);
            }

            /**
             * 发送带附件的邮件
             * @param to
             * @param subject
             * @param content
             * @param filePath
             */
    public void sendAttachmentsMail(String to,String subject,String content,String filePath)
            throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        //添加附件
        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName,file);
        //发送邮件
        mailSender.send(message);
    }

    /**
     * 发送带多个附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePaths 多个文件路径
     */
    public void sendAttachmentsMail(String to, String subject, String content, String... filePaths)
            throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);

        for (String filePath : filePaths) {
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
            helper.addAttachment(fileName, file);
        }
        mailSender.send(message);
    }
}

