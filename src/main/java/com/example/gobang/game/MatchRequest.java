package com.example.gobang.game;

import lombok.Data;

/**
 * @author xinyu
 * @version 1.0
 * @description: TODO
 * @date 2024/2/19 20:38
 */
//表示websocket的匹配请求
    @Data
public class MatchRequest {
    private String message="";
}
