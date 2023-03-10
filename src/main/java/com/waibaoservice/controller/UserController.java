package com.waibaoservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.waibaoservice.pojo.User;
import com.waibaoservice.service.UserService;
import com.waibaoservice.utils.WeiXinUtils.WeiXinRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author DJS
 * Date create 22:54 2023/2/19
 * Modified By DJS
 **/

@RestController
@RequestMapping("/login")
public class UserController {
    @Autowired
    UserService userService;

    public UserController() {}

    @PostMapping("/userLogin")
    @ResponseBody
    public User login(@RequestBody User user) throws JsonProcessingException {
        // 向微信发送请求
        String json_str = WeiXinRequestUtils.getUserInfo(user.getCode());
        System.out.println(json_str);
        return userService.userLogin(json_str);
    }
}
