package com.example.gobang.game;

import lombok.Data;

/**
 * @author xinyu
 * @version 1.0
 * @description: TODO
 * @date 2024/2/19 20:32
 */
//表示websocket的匹配响应
    @Data
public class MatchResponse {
    private boolean ok;
    private String reason;
    private String message;
}
