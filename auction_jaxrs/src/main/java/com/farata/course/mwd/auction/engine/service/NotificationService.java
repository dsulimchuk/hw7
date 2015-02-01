package com.farata.course.mwd.auction.engine.service;


import com.farata.course.mwd.auction.entity.User;

import javax.ejb.Singleton;

@Singleton
public class NotificationService {

    public NotificationService() {
    }

    public void sendNotification(User user, Notification notification) {
        System.out.println("[Email to: " + user.getEmail() + "] :" + Notifications.getMsg(notification));
    }
}