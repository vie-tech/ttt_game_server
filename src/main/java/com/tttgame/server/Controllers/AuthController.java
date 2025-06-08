package com.tttgame.server.Controllers;


import com.tttgame.server.DTO.LoginDTO;
import com.tttgame.server.DTO.SignupDTO;
import com.tttgame.server.Service.AuthService;
import com.tttgame.server.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping("/validate/session")
    @PreAuthorize("permitAll")
    public ResponseEntity<?> validateSession() {
        Map<String, Object> user = userService.getUserObject();
        boolean isAuthenticated = user.isEmpty();
        return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "valid user",
                        "status", 200,
                        "isAuthenticated", !isAuthenticated,
                        "user", user.get("user"),
                        "email", user.get("email")
                )
        );
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO request,
                                       HttpServletResponse response) {
        authService.loginUser(request, response);
        Map<String, Object> user = userService.getUserObject();
         return ResponseEntity.ok(Map.of("success", true, "isAuthenticated", true ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupDTO request,
                                      HttpServletResponse response) {
        authService.registerUser(request, response);
        return ResponseEntity.ok(Map.of("success", true, "message", "account " +
                "created", "status", HttpStatus.CREATED.value()));
    }
}
