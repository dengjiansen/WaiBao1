package com.waibaoservice.controller;

import com.waibaoservice.pojo.Device;
import com.waibaoservice.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author DJS
 * Date create 19:03 2023/3/14
 * Modified By DJS
 **/

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @PostMapping("/getDeviceList")
    @ResponseBody
    public List<Integer> getDeviceList(@RequestBody Device device) {
        return deviceService.getDeviceList(device.getOpenid());
    }

    @PostMapping("/addDevice")
    @ResponseBody
    public boolean addDevice(@RequestBody Device device) {
        return deviceService.addDevice(device);
    }
}
