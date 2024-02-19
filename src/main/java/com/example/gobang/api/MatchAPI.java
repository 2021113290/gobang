package com.example.gobang.api;

import com.example.gobang.game.MatchRequest;
import com.example.gobang.game.MatchResponse;
import com.example.gobang.game.OnlineUserManager;
import com.example.gobang.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author xinyu
 * @version 1.0
 * @description: 处理匹配的websocket请求
 * @date 2024/2/19 16:36
 */
@Component
public class MatchAPI extends TextWebSocketHandler {
    private ObjectMapper objectMapper=new ObjectMapper();
    @Autowired
    private OnlineUserManager onlineUserManager;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//玩家上线，加入到 onlineUserManager
        try {
            //        1.先获取当前用户身份信息,判断是否已登陆过
//        HttpSession中的 Attribute拿到 WebSocketSession中
            User user= (User) session.getAttributes().get("user");
            WebSocketSession tmpsession=onlineUserManager.getFromGameHall(user.getUserId());
            if (tmpsession!=null){
                MatchResponse matchResponse=new MatchResponse();
                matchResponse.setOk(false);
                matchResponse.setReason("禁止多开");
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(matchResponse)));
                session.close();
                return;
            }
//        2.将玩家设置为在线状态
            onlineUserManager.enterGameHall(user.getUserId(),session);
            System.out.println("玩家"+user.getUsername()+"进入游戏大厅");
        }catch (NullPointerException e){
            e.printStackTrace();
            MatchResponse matchResponse=new MatchResponse();
            matchResponse.setOk(false);
            matchResponse.setReason("尚未登陆！不能进行后续匹配功能");
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(matchResponse)));
        }

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//实现处理开始匹配请求和处理停止匹配请求
        User user=(User) session.getAttributes().get("user");
//        获取到客户端给服务器发送的数据
        String payload=message.getPayload();
//        获取到的数据载荷是json格式字符串，要转成java对象
        MatchRequest matchRequest=objectMapper.readValue(payload,MatchRequest.class);
        MatchResponse matchResponse=new MatchResponse();
        if (matchRequest.getMessage().equals("startMatch")){
//            将玩家信息放入匹配队列
            matchResponse.setOk(true);
            matchResponse.setMessage("startMatch");
        }else if (matchRequest.getMessage().equals("stopMatch")){
//            将玩家信息移出匹配队列
            matchResponse.setOk(true);
            matchResponse.setMessage("stopMatch");
        }else {
            matchResponse.setOk(false);
            matchResponse.setReason("非法匹配请求");
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        try {
            User user= (User) session.getAttributes().get("user");
            WebSocketSession tmpsession=onlineUserManager.getFromGameHall(user.getUserId());
            if (tmpsession==session){
                onlineUserManager.exitGameHall(user.getUserId());
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            MatchResponse matchResponse=new MatchResponse();
            matchResponse.setOk(false);
            matchResponse.setReason("尚未登陆！不能进行后续匹配功能");
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(matchResponse)));
        }

//玩家下线，从 onlineUserManager 删除

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        try {
            //玩家下线，从 onlineUserManager 删除
            User user= (User) session.getAttributes().get("user");
            WebSocketSession tmpsession=onlineUserManager.getFromGameHall(user.getUserId());
            if (tmpsession==session){
                onlineUserManager.exitGameHall(user.getUserId());
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            MatchResponse matchResponse=new MatchResponse();
            matchResponse.setOk(false);
            matchResponse.setReason("尚未登陆！不能进行后续匹配功能");
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(matchResponse)));
        }

    }
}
