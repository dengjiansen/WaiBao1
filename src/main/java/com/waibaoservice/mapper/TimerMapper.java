package com.waibaoservice.mapper;

import com.waibaoservice.pojo.Timer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// 为用户添加定时
@Repository
public interface TimerMapper {
    int addTimer(@Param("openid")String openid,
                 @Param("device_id")String deviceId,
                 @Param("unionid")String unionId,
                 @Param("end_time")String dateStr);

    int removeTimerByDeviceId(@Param("device_id")String device_id);
    int updateTimerByDeviceId(@Param("device_id")String deviceId,
                              @Param("end_time")String dateStr);

    List<Timer> selectAllTimer();
    Timer selectTimerByDeviceId(@Param("device_id")String deviceId);
    Timer selectAll();
}
