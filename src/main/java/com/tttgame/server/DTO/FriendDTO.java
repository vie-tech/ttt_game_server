package com.tttgame.server.DTO;

import com.tttgame.server.Enums.RequestStatus;
import com.tttgame.server.Model.Friends;

import java.time.LocalDateTime;

public record FriendDTO(UserDTO user, UserDTO friend, RequestStatus status, LocalDateTime time_sent, String friendshipId) {
    public FriendDTO(Friends friends){
        this(new UserDTO(friends.getUser()), new UserDTO(friends.getFriend()), friends.getStatus(), friends.getCreatedAt(), friends.getFriendshipId());
    }
}
