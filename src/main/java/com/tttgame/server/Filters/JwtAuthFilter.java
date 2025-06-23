package com.tttgame.server.Filters;

import com.tttgame.server.Model.Users;
import com.tttgame.server.Service.CustomUserDetailsService;
import com.tttgame.server.Service.UserService;
import com.tttgame.server.Utils.CookieUtil;
import com.tttgame.server.Utils.JwtUtil;
import com.tttgame.server.Utils.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String token = null;

        if (request.getServletPath().startsWith("/api/auth/login") ||
                        request.getServletPath().startsWith("/api/auth/register") || request.getServletPath().startsWith("/wss")) {
                    filterChain.doFilter(request, response);
                    return;
                }

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(CookieUtil.doesCookieExist(cookie)){
                    token = cookie.getValue();
                    break;
                }
            }
        }


        if (token != null) {
            try {
                String username = jwtUtil.extractUserName(token);
                System.out.println(username);
                if (username != null && userService.getCurrentUser() == null) {
                    UserDetails user = customUserDetailsService.loadUserByUsername(username);
                    if (jwtUtil.validateToken(token, user)) {
                        userService.setCurrentUser(user);
                    }
                }
            } catch (Exception e) {
                ResponseUtil.sendErrorResponse(response, HttpStatus.UNAUTHORIZED,
                        "Invalid_token", "Authentication failed: Invalid or expired token");
                return;
            }
        }else{
            System.out.println("No token found: JWT FILTER");
            response.sendError(401, "No token found");
        }


        filterChain.doFilter(request, response);
    }
}
