package com.waibaoservice.service.impl;

import com.waibaoservice.mapper.DeviceMapper;
import com.waibaoservice.pojo.Device;
import com.waibaoservice.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author DJS
 * Date create 19:06 2023/3/14
 * Modified By DJS
 **/

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceMapper mapper;

    @Override
    public boolean addDevice(Device device) {
        int ret = mapper.addDevice(device.getDevice_id(), device.getOpenid());
        return ret == 1;
    }

    @Override
    public List<Integer> getDeviceList(String openid) {
        final List<Device> deviceList = mapper.getDeviceList(openid);
        List<Integer> list = new LinkedList<>();
        for (Device device : deviceList) {
            list.add(device.getDevice_id());
        }
        return list;
    }
}
