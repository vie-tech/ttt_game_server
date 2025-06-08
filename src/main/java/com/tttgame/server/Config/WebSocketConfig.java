package com.tttgame.server.Config;


import com.tttgame.server.Sockets.CustomHandshakeHandler;
import com.tttgame.server.Sockets.JwtHandShakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.*;

import java.security.Principal;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    JwtHandShakeInterceptor jwtHandShakeInterceptor;

    @Autowired
    CustomHandshakeHandler customHandshakeHandler;


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/wss")
                .addInterceptors(jwtHandShakeInterceptor)
                .setHandshakeHandler(customHandshakeHandler)
                .setAllowedOriginPatterns("*")
                .withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }


   /* @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message,
                                      MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message,
                                StompHeaderAccessor.class);
                if (accessor != null && accessor.getUser() == null) {
                    Principal user =
                            (Principal) accessor.getSessionAttributes().get(
                                    "user");
                    if (user != null) {
                        accessor.setUser(user);
                    }
                }
                return message;
            }
        });
    }*/
}
