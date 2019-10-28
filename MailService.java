package spring_boot.EXAMPLE_SPRING_BOOT.mail;

import javax.mail.MessagingException;

public interface MailService {

    public void sendMail(String to,String subject,String content);

    public void sendHtmlMail(String to,String subject,String content) throws MessagingException;

    public void sendAttachmentsMail(String to, String subject, String content, String filePath);

    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);
}
