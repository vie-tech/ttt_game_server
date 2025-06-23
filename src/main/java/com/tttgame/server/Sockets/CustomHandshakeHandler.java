package com.tttgame.server.Sockets;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;

@Component
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler webSocketHandler, Map<String, Object> attributes){
        String username = (String)  attributes.get("user");
        String uid = (String) attributes.get("user_id");
        if(username == null || uid == null){
            return null;
        }

        return ()->uid;
    }
}
