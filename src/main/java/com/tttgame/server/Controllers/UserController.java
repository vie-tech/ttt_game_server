package com.tttgame.server.Controllers;


import com.tttgame.server.DTO.UserDTO;
import com.tttgame.server.Model.Users;
import com.tttgame.server.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> retrieveAllUsers(){
        List<Users> users = userService.retrieveAllUsers();
        List<UserDTO> userDTO = users.stream().map(UserDTO::new).toList();
        return ResponseEntity.ok(Map.of("success", true, "users", userDTO));
    }
}
