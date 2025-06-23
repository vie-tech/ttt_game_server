package com.tttgame.server.DTO;
import com.tttgame.server.Model.Messages;
import java.util.Date;

public record MessagesDTO(UserDTO senderId, UserDTO receiverId, Date timeSent, String content) {
    public MessagesDTO(Messages messages){
        this(new UserDTO(messages.getSender()), new UserDTO(messages.getReceiver()), messages.getTimeSent(), messages.getContent());
    }
}
