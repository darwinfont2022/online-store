package com.bee.store.notification.email;

public interface SendNotification {
    void send(String to, String subject, String body);
}
