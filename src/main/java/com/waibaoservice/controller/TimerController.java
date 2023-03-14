package com.waibaoservice.controller;

import com.waibaoservice.pojo.Timer;
import com.waibaoservice.service.TimerService;
import com.waibaoservice.utils.MqttUtils.MqttUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author DJS
 * Date create 23:57 2023/3/9
 * Modified By DJS
 **/

@RestController
@RequestMapping("/timer")
public class TimerController {

    @Autowired
    TimerService timerService;

    @PostMapping("/getEndTime")
    @ResponseBody
    public String getEndTime(@RequestBody Timer timer) {
        if (timer != null) {
            System.out.println("用户:" + timer.getOpenid() + "访问getEndTime");
            String endTime = timerService.getEndTime(timer);
            System.out.println("endTime : " + endTime);
            return endTime;
        }
        return "no task";
    }

    // 为用户添加定时器功能
    @PostMapping("/setTimer")
    @ResponseBody
    public boolean setTimer(@RequestBody Timer timer) {
        if (timer != null) {
            System.out.println(timer);
            System.out.println("用户:" + timer.getOpenid() + "访问setTimer");
            return timerService.setTimer(timer);
        }
        return false;
    }

    // 为用户取消定时功能
    @PostMapping("/removeTimer")
    @ResponseBody
    public boolean removeTimer(@RequestBody Timer timer) {
        System.out.println(timer);
        if (timer != null) {
            System.out.println("用户:" + timer.getOpenid() + "访问removeTimer");
            return timerService.cancelTimer(timer);
        }
        return false;
    }

}
