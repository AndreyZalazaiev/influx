package andrew.projects.influx.Service;

import andrew.projects.influx.Domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private static final String url ="http://localhost:8080";
    private final String SUBJECT = "Influx Activation Code";
    private final String MSG_TEXT = "Hello! Click here to activate your account"+url+"+/activate/";
    private final JavaMailSender mailSender;

    public void sendValidationCode(User a) {
        sendEmail(a.getEmail(), SUBJECT, MSG_TEXT + a.getEmailConfirmation());
    }

    public void sendEmail(String to, String subject, String text) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);

        msg.setSubject(subject);
        msg.setText(text);
        new Thread(new asyncSendSimpleMessage(msg)).start();
    }

    class asyncSendSimpleMessage implements Runnable {
        SimpleMailMessage sm;

        public asyncSendSimpleMessage(SimpleMailMessage msg) {

            this.sm = msg;
        }

        public void run() {
            mailSender.send(sm);
        }

    }

}