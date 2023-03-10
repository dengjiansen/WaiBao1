package com.waibaoservice.controller;

import com.waibaoservice.pojo.User;
import com.waibaoservice.utils.WeiXinUtils.WeiXinRequestUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author DJS
 * Date create 22:10 2023/3/10
 * Modified By DJS
 **/

// 订阅消息控制器
@RestController
@RequestMapping("/sub")
public class SubscribeController {

    @PostMapping("/subscribe")
    @ResponseBody
    public boolean subscribeMessage(@RequestBody User user) {
        final String s = WeiXinRequestUtils.pushMessage(user.getOpenid());
        return s.equals("ok");
    }
}
