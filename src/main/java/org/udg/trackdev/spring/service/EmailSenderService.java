package org.udg.trackdev.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.udg.trackdev.spring.controller.exceptions.ServiceException;
import org.udg.trackdev.spring.entity.Email;
import org.udg.trackdev.spring.repository.EmailRepository;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

/**
 * The type Email sender service.
 */
@Service
@Slf4j
public class EmailSenderService extends BaseServiceUUID<Email,EmailRepository>{

    private static final String LINK_RECOVERY = "http://localhost:3000/auth/password?email=";

    private final JavaMailSender javaMailSender;

    /**
     * Instantiates a new Email sender service.
     *
     * @param javaMailSender the java mail sender
     */
    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Send register email.
     *
     * @param username the username
     * @param to       the to
     * @param tempPass the temp pass
     */
    public void sendRegisterEmail(String username, String to, String tempPass) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(
                    String.format("TRACKDEV - Benvingut a TrackDev, %s!", username)
            );
            helper.setText(
                    String.format("Benvingut a TrackDev, <b>%s</b>!<br><br>Com a estudiant de l'assignatura de Projecte de Software" +
                            " de la UdG has estat donat d'alta a la plataforma amb les seguents credencials:<br>" +
                            "Usuari: <b>%s</b><br>Contrasenya: <b>%s</b><br><br>" +
                            "Si us plau, no responguis aquest missatge, es un enviament automatic.<br><br><b>Trackdev.</b>", username, username, tempPass),
                    true
            );
            javaMailSender.send(message);

            Email email = new Email();
            email.setDestination(to);
            email.setTimestamp(LocalDateTime.now());
            this.repo.save(email);
        } catch (Exception e) {
            log.error("Could not send email to " + to, e);
            throw new ServiceException("Could not send register email to " + to + ". Please try again later.");
        }
    }

    /**
     * Send recovery email.
     *
     * @param email    the email
     * @param tempCode the temp code
     */
    public void sendRecoveryEmail(String email, String tempCode) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(
                    String.format("TRACKDEV - Recuperació de contrasenya")
            );
            helper.setText(
                    String.format("Hola!<br><br>Has demanat recuperar la teva contrasenya de <b>TrackDev</b>. Si no has estat tu, ignora aquest missatge.<br><br>" +
                            "Si has estat tu, pots restaurar la teva contrasenya introduint el següent codi a la pàgina de recuperació de contrasenya<br>" +
                            "Codi de recuperació: <b>%s</b><br>Accedeix al següent <a href=%s%s>link</a><br><br>" +
                            "Si us plau, no responguis aquest missatge, és un enviament automàtic.<br><br><b>Trackdev.</b>", tempCode, LINK_RECOVERY, email),
                    true
            );
            javaMailSender.send(message);

            Email log = new Email();
            log.setDestination(email);
            log.setTimestamp(LocalDateTime.now());
            this.repo.save(log);
        } catch (Exception e) {
            log.error("Could not send email to " + email, e);
            throw new ServiceException("Could not send register email to " + email + ". Please try again later.");
        }
    }

}
