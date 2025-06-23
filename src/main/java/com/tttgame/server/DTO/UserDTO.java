package com.tttgame.server.DTO;

import com.tttgame.server.Model.Users;

public record UserDTO(String uid, String username, String email) {
    public UserDTO(Users user){
            this(user.getUid(), user.getUsername(), user.getEmail());
    }
}
