package com.example.gobang.model;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author xinyu
 * @version 1.0
 * @description: TODO
 * @date 2024/2/16 12:34
 */
@Mapper
public interface UserMapper {
    void insert(User user);
    User selectByName(String username);
}
