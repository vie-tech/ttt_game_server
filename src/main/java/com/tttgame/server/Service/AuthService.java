package com.tttgame.server.Service;

import com.tttgame.server.DTO.LoginDTO;
import com.tttgame.server.DTO.SignupDTO;
import com.tttgame.server.Model.Users;
import com.tttgame.server.Repository.UserRepository;
import com.tttgame.server.Utils.JwtUtil;
import io.jsonwebtoken.security.Password;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {


    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CookieService cookieService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;
    public AuthService(JwtUtil jwtUtil,
                       AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       CookieService cookieService,
                       PasswordEncoder passwordEncoder
    ) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.cookieService = cookieService;
        this.passwordEncoder = passwordEncoder;
    }


    public void loginUser(LoginDTO request, HttpServletResponse response) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        Users users = (Users) authentication.getPrincipal();
        String token = jwtUtil.generateToken(users.getUsername(),users.getUID());
        Cookie cookie = cookieService.createCookie("my_access_token", token);
       /* userService.setCurrentUser(users);*/
        response.addCookie(cookie);
    }

    public void registerUser(SignupDTO request, HttpServletResponse response) {
        Users user = new Users(request.username(), request.password(),
                request.email());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser.getUsername(), savedUser.getUID());
        Cookie cookie = cookieService.createCookie("my_access_token", token);
        response.addCookie(cookie);
    }


}
