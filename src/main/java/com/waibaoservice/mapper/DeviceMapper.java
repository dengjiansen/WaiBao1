package com.waibaoservice.mapper;

import com.waibaoservice.pojo.Device;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DJS
 * Date create 19:07 2023/3/14
 * Modified By DJS
 **/

@Repository
public interface DeviceMapper {
    int addDevice(@Param("device_id")Integer deviceId, @Param("openid")String openId);
    List<Device> getDeviceList(@Param("openid") String openId);
}
