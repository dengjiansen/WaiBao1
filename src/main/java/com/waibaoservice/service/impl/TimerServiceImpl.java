package com.waibaoservice.service.impl;

import com.waibaoservice.mapper.TimerMapper;
import com.waibaoservice.pojo.Timer;
import com.waibaoservice.service.TimerService;
import com.waibaoservice.utils.DateUtils.DateUtils;
import com.waibaoservice.utils.MqttUtils.MqttUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author DJS
 * Date create 0:12 2023/3/10
 * Modified By DJS
 **/

@Service
public class TimerServiceImpl implements TimerService {
    @Autowired
    private TimerMapper mapper;

    public TimerServiceImpl() {}

    // 设置定时任务
    @Override
    public boolean setTimer(Timer timer) {
        // 获取任务结束的时间字符串
        final String positive_time = timer.getPositive_time();
        String endTime = DateUtils.getEndTimeStr(new Date(), positive_time, ":");
        System.out.println(endTime);
        // 将定时器任务添加到数据库, 如果数据库中本身存在任务，需要更新
        Timer t = mapper.selectTimerByDeviceId(timer.getDevice_id());
        if (t == null) {
            // 需要加锁
            int ret;
            synchronized (this) {
                ret = mapper.addTimer(
                        timer.getOpenid(),
                        timer.getDevice_id(),
                        timer.getUnionid(),
                        endTime
                );
            }
            if (ret == 1) {
                System.out.println("添加定时任务成功");
                // 异步发送Mqtt消息
                new Thread(() -> MqttUtils.publish(endTime)).start();
            }
            else {
                System.out.println("添加定时任务失败");
                return false;
            }
        }
        else {
            // 更新定时任务
            int ret;
            synchronized (this) {
                ret = mapper.updateTimerByDeviceId(
                        timer.getDevice_id(),
                        endTime
                );
            }
            if (ret == 1) System.out.println("更新定时任务成功");
            else {
                System.out.println("更新定时任务失败");
                return false;
            }
        }
        return true;
    }

    // 取消定时任务
    @Override
    public boolean cancelTimer(Timer timer) {
        synchronized (this) {
            // 异步发送消息取消
            new Thread(() -> MqttUtils.publish("cancel")).start();
            int res;
            res = mapper.removeTimerByDeviceId(timer.getDevice_id());
            if (res == 1) {
                System.out.println("移除定时任务成功");
                return true;
            }
            else {
                System.out.println("移除定时任务失败");
                return false;
            }
        }
    }

    // 小程序登录时获取timer结束时间， 如果没有设置时间返回"no task"
    @Override
    public String getEndTime(Timer timer) {
        Timer t = mapper.selectTimerByDeviceId(timer.getDevice_id());
        if (t != null) {
            System.out.println("endTime : " + t.getEnd_time());
            return t.getEnd_time();
        }
        return "no task";
    }
}
