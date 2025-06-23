package com.tttgame.server.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PrivateMessageDTO {

    private String sender;
    private String content;
    private String receiver;
    private String receiverUid;
    private String roomId;

}
