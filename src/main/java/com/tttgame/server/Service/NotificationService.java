package com.tttgame.server.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    SimpMessageSendingOperations notificationTemplate;

    public void sendNotificationToUser(String receiverId, Map<String, Object> message){
        notificationTemplate.convertAndSendToUser(
                receiverId,
                "/queue/notifications",
                message
        );
    }
}
