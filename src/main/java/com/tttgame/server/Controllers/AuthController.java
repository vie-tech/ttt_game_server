package com.tttgame.server.Controllers;


import com.tttgame.server.DTO.LoginDTO;
import com.tttgame.server.DTO.SignupDTO;
import com.tttgame.server.Service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/validate/session")
    @PreAuthorize("permitAll")
    public ResponseEntity<?> validateSession() {
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "valid user",
                "status", 200,
                "isAuthtenticated", true)
        );
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO request,
                                       HttpServletResponse response) {
        authService.loginUser(request, response);
        return ResponseEntity.ok(Map.of("success", true, "message", "user " +
                "logged in"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupDTO request,
                                      HttpServletResponse response) {
        authService.registerUser(request, response);
     return  ResponseEntity.ok(Map.of("success", true, "message", "account " +
                "created", "status", HttpStatus.CREATED.value()));
    }
}
