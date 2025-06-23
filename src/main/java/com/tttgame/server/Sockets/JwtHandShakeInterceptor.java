package com.tttgame.server.Sockets;

import com.tttgame.server.Utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class JwtHandShakeInterceptor implements HandshakeInterceptor {

    @Autowired
    JwtUtil jwtUtil;


    @Override
       public boolean beforeHandshake(ServerHttpRequest request,
                                      ServerHttpResponse response,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) throws Exception {
           if (request instanceof ServletServerHttpRequest servletRequest) {
               HttpServletRequest servletReq = servletRequest.getServletRequest();
               Cookie[] cookies = servletReq.getCookies();
               if (cookies != null) {
                   for (Cookie cookie : cookies) {
                       if ("my_access_token".equals(cookie.getName())) {
                           String token = cookie.getValue();
                           String username = jwtUtil.extractUserName(token);
                           String uid = jwtUtil.extractUid(token);

                           attributes.put("user", username);  // Save username to handshake attributes
                           attributes.put("user_id", uid);
                           break;
                       }
                   }
               }
           }
           return true;
       }
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
