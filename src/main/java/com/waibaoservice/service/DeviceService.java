package com.waibaoservice.service;

import com.waibaoservice.pojo.Device;

import java.util.List;

public interface DeviceService {
    boolean addDevice(Device device);
    List<Integer> getDeviceList(String openid);
}
