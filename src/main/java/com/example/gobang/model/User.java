package com.example.gobang.model;

import lombok.Data;

/**
 * @author xinyu
 * @version 1.0
 * @description: TODO
 * @date 2024/2/16 12:31
 */
@Data
public class User {
    private int userId;
    private String username;
    private String password;
    private int score;
    private int totalCount;
    private int winCount;
}
