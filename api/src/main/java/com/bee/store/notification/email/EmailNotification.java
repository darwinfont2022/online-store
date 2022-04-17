package com.bee.store.notification.email;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class EmailNotification implements SendNotification {

    @Override
    public void send(String to, String subject, String body) {
        log.info("Sending email to {} with subject {} and body {}", to, subject, body);
    }
}
