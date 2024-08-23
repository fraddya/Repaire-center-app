package com.rcs.service.Impl;

import com.google.firebase.FirebaseException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.rcs.domain.NotificationMassage;
import com.rcs.service.FirebaseMessagingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FirebaseMessagingServiceImpl implements FirebaseMessagingService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    public String sendNotificationByToken(NotificationMassage notificationMassage) {

        log.info("notificationMassage service ====> {}",notificationMassage);
        Notification notification = Notification
                .builder()
                .setTitle(notificationMassage.getTitle())
                .setBody(notificationMassage.getBody())
                .setImage(notificationMassage.getImage())
                .build();

        Message message = Message
                .builder()
                .setToken(notificationMassage.getRecipientToken())
                .setNotification(notification)
                .putAllData(notificationMassage.getData())
                .build();

        try {
            firebaseMessaging.send(message);
            return "success sending Notification";
        }catch (FirebaseException e) {
            e.printStackTrace();
            return "error sending Notification";
        }
    }
}
