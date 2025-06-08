package com.tttgame.server.Service;


import com.tttgame.server.DTO.PrivateMessageDTO;
import com.tttgame.server.Model.Messages;
import com.tttgame.server.Model.Users;
import com.tttgame.server.Repository.MessageRepository;
import com.tttgame.server.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class MessageService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    public void saveMessageToDatabase(PrivateMessageDTO payload, Principal principal){
        Messages message = new Messages();
        Users sender = userRepository.findByUID(principal.getName())
         .orElseThrow(() -> new IllegalArgumentException("Sender UID not found: " + principal.getName()));

     Users receiver = userRepository.findByUID(payload.getReceiver())
         .orElseThrow(() -> new IllegalArgumentException("Receiver UID not found: " + payload.getReceiver()));

        System.out.println("sender found by UID"+ sender);
        System.out.println("receiver found by UID"+ receiver);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(payload.getContent());
        messageRepository.save(message);

    }
}
