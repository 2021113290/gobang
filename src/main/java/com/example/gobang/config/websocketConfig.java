package com.example.gobang.config;

import com.example.gobang.api.MatchAPI;
import com.example.gobang.api.TestAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * @author xinyu
 * @version 1.0
 * @description: TODO
 * @date 2024/2/16 11:04
 */
@Configuration
@EnableWebSocket
public class websocketConfig implements WebSocketConfigurer {
    @Autowired
    private  TestAPI testAPI;
    @Autowired
    private MatchAPI matchAPI;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(testAPI,"/test");
        registry.addHandler(matchAPI,"/findMatch")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }
}
