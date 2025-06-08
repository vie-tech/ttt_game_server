package com.tttgame.server.Controllers;


import com.tttgame.server.DTO.ChatMessageDTO;
import com.tttgame.server.DTO.PrivateMessageDTO;
import com.tttgame.server.Model.Messages;
import com.tttgame.server.Model.Users;
import com.tttgame.server.Repository.MessageRepository;
import com.tttgame.server.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {
    @Autowired
    SimpMessageSendingOperations messageTemplate;


    @Autowired
    MessageService messageService;

    @MessageMapping("/chat.send_message")
    @SendTo("/topic/public")
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO payload){
        return payload;
    }

    @MessageMapping("/chat.private")
    public void sendPrivateMessage(@Payload PrivateMessageDTO message, Principal principal){

        messageService.saveMessageToDatabase(message, principal);
        messageTemplate.convertAndSendToUser(
                message.getReceiver(),
                "/queue/messages",
                message
        );
    }
}
