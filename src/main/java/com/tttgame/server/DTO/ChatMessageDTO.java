package com.tttgame.server.DTO;


import lombok.Getter;
import lombok.Setter;


public class ChatMessageDTO {
    private String content;
    private String sender;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
