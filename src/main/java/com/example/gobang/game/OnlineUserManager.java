package com.example.gobang.game;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xinyu
 * @version 1.0
 * @description: TODO
 * @date 2024/2/19 17:10
 */
@Component
public class OnlineUserManager {
    //        key-用户id  value-用户会话
    private ConcurrentHashMap<Integer, WebSocketSession> gameHall=new ConcurrentHashMap<>();
    public void enterGameHall(int userId,WebSocketSession webSocketSession){
        gameHall.put(userId,webSocketSession);
    }
    public void exitGameHall(int userId){
        gameHall.remove(userId);
    }
    public WebSocketSession getFromGameHall(int userId){
        return gameHall.get(userId);
    }


}
