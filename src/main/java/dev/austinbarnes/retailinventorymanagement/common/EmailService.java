package dev.austinbarnes.retailinventorymanagement.common;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * EmailService is a Spring service that provides methods for sending emails.
 * It uses JavaMailSender to send emails asynchronously.
 * The service can send activation emails and general emails with HTML content.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${app.base.url}")
    private String baseUrl;

    /**
     * Sends an activation email to the specified recipient with a unique token.
     *
     * @param to   the recipient's email address
     * @param token the unique token for account activation
     */
    @Async
    public void sendActivationEmail(String to, UUID token){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("Activate Your Account");

            String link = baseUrl + "/api/v1/auth/activate?token=" + token;

            String htmlContent = """
                    <html>
                        <body>
                            <h1>Welcome!</h1>
                            <p>Thank you for registering. Please click the link below to activate your account.</p>
                            <p><a href="%s">Activate Your Account</a></p>
                            <p>If the link doesn't work, copy and paste this URL into your browser:</p>
                            <p>%s</p>
                            <p>This link will expire after 24 hours.</p>
                            <p>Take care!</p>
                        </body>
                    </html>
                    """.formatted(link, link);

            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Activation email sent successfully to: {}", to);
        } catch(MessagingException e) {
            log.error("Failed to send activation email {}", to, e);
            throw new RuntimeException("Failed to send activation email", e);
        }
    }

    /**
     * Sends an email with the specified subject and HTML content to the specified recipient.
     *
     * @param to          the recipient's email address
     * @param subject     the subject of the email
     * @param htmlContent the HTML content of the email
     */
    @Async
    public void sendEmail(String to, String subject, String htmlContent){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Email sent successfully to: {}", to);
        } catch(MessagingException e) {
            log.error("Failed to send email {}", to, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
