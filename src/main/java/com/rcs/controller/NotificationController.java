package com.rcs.controller;

import com.rcs.domain.NotificationMassage;
import com.rcs.service.FirebaseMessagingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class NotificationController {

    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    @PostMapping("${app.endpoint.sendNotifivation}")
    public String sendNotification(@RequestBody NotificationMassage notificationMassage) {
        log.info("notificationMassage ====> {}",notificationMassage);
        return firebaseMessagingService.sendNotificationByToken(notificationMassage);
    }
}
