package com.rcs.service;

import com.rcs.domain.NotificationMassage;

public interface FirebaseMessagingService {

    String sendNotificationByToken(NotificationMassage notificationMassage);
}
