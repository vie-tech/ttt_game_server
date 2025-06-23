package com.tttgame.server.Controllers;


import com.tttgame.server.DTO.MessagesDTO;
import com.tttgame.server.Model.Messages;
import com.tttgame.server.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    MessageService messageService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllMessages(@RequestParam String friendId){
        List<Messages> messages = messageService.getAllMessagesBetweenFriends(friendId);
        List<MessagesDTO> messagesDTO = messages.stream().map(MessagesDTO::new).toList();

        return ResponseEntity.ok(Map.of("success", true, "messages", messagesDTO));
    }


}
