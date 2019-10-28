package spring_boot.EXAMPLE_SPRING_BOOT.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.file.FileSystem;
import java.sql.Date;
import java.time.LocalDate;

@PropertySource("classpath:send_mail.properties")
public class MailServiceImpl implements MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender sender;
    private MimeMessage mimeMessage;

    @Value("${mail.from}")
    private String from;

    @Override
    public void sendMail(String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        mailMessage.setSentDate(Date.valueOf(LocalDate.now()));

        try {
            sender.send(mailMessage);
            logger.info("YOU SEND MESSAGE");
        }
        catch(Exception e)
        {
            logger.error("YOU DON'T SEND MESSAGE",e);
        }
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content)  {
        try {
        mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content);

        sender.send(mimeMessage);

        logger.info("YOU SEND HTML MAIL");
        } catch (MessagingException e) {
            logger.error("YOU DON'T SEND HTML MAIL",e);
            e.printStackTrace();
        }

    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        try
        {
            mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setText(content);
            helper.setSentDate(Date.valueOf(LocalDate.now()));



            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName,file);

            sender.send(mimeMessage);
            logger.info("SEND MESSAGE");
        }
        catch(MessagingException e)
        {
            logger.error("DON'T SEND MESSAGE");
            e.printStackTrace();
        }
    }

    @Override
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {

        try {
                mimeMessage = sender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
                helper.setFrom(from);
                helper.setTo("sfsdfds@df.fs");
                helper.setSubject("adnininfsdf");
                helper.setText("fsdfdsfd",true);

            FileSystemResource resource = new FileSystemResource(new File(rscPath));
            helper.addInline("conteztID",resource);

            sender.send(mimeMessage);

            logger.info("SEND MESSAGE");
            }
        catch(MessagingException e)
        {
            logger.error("DOn'T SEND MESSAGE");
        }
    }
}
