package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.configuration.AlertingProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertService {
    private final JavaMailSender mailSender;
    private final AlertingProperties alertingProperties;

    private final ConcurrentHashMap<String, Instant> lastAlerts =
            new ConcurrentHashMap<>();

    public void sendAlert(
            String type,
            String subject,
            String message
    ) {
        if (!alertingProperties.enabled()) {
            return;
        }

        String alertKey = type + ":" + subject;

        Instant now = Instant.now();
        Instant previous = lastAlerts.get(alertKey);

        if (previous != null &&
                now.isBefore(previous.plusSeconds(
                        alertingProperties.cooldownMinutes() * 60L))) {
            return;
        }

        try {
            SimpleMailMessage email = new SimpleMailMessage();

            email.setFrom(alertingProperties.from());

            email.setTo(
                    alertingProperties.recipients().split("\\s*,\\s*")
            );

            email.setSubject(
                    "[ALERT][" + type + "] " +
                            alertingProperties.applicationName() + " - " + subject
            );

            email.setText(
                    "Application : " + alertingProperties.applicationName() +
                            "\nType : " + type +
                            "\nDate : " + now +
                            "\n\nDétails :\n" + message
            );

            mailSender.send(email);

            lastAlerts.put(alertKey, now);

            log.warn("Alert email sent: {}", subject);

        } catch (Exception e) {
            log.error("Unable to send alert email: {}", subject, e);
        }
    }


//    A GARDER ?
    @Value("${alerting.email.from}")
    private String from;

    @Value("${alerting.email.to}")
    private String to;

    public void sendAlert(String subject, String message) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom(from);
        email.setTo(to);
        email.setSubject("[ALERT] " + subject);
        email.setText(message);

        mailSender.send(email);
    }
}
