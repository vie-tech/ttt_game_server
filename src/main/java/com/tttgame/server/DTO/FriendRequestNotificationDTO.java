package com.tttgame.server.DTO;

import com.tttgame.server.Enums.RequestStatus;

public record FriendRequestNotificationDTO(String senderId, String sender, String id, RequestStatus status, String message) {
}
