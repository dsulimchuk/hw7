package com.ds.auction.service;

import com.ds.auction.model.User;

public class NotificationService {

    public NotificationService() {
    }

    public void sendNotification(User user, Notification notification) {
        System.out.println("[Email to: " + user.getEmail() + "] :" + Notifications.getMsg(notification));
    }
}