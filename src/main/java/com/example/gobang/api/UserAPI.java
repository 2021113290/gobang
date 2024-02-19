package com.example.gobang.api;

import com.example.gobang.model.User;
import com.example.gobang.model.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xinyu
 * @version 1.0
 * @description: TODO
 * @date 2024/2/18 11:26
 */
@RestController
public class UserAPI {
    @Resource
    private UserMapper userMapper;
    @PostMapping("/login")
    @ResponseBody
    public Object login(String username, String password, HttpServletRequest req){
        User user=userMapper.selectByName(username);
        System.out.println("[login] username="+username);
        if (user==null||!user.getPassword().equals(password)){
            System.out.println("登陆失败");
            return new User();
        }
        HttpSession httpSession=req.getSession(true);
        httpSession.setAttribute("user",user);
        return user;
    }
    @PostMapping("/register")
    @ResponseBody
    public Object register(String username,String password){
       try {
           User user=new User();
           user.setUsername(username);
           user.setPassword(password);
           userMapper.insert(user);
           return user;
       }catch (org.springframework.dao.DuplicateKeyException e){
           User user=new User();
           return user;
       }

    }
    @GetMapping("/userinfo")
    @ResponseBody
    public Object getUserInfo(HttpServletRequest req){
        try {
            HttpSession httpSession=req.getSession(false);
            User user=(User) httpSession.getAttribute("user");
            return user;
        }catch (NullPointerException e){
            return new User();
        }
    }
}
