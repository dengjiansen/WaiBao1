package com.waibaoservice.timertask;

import com.waibaoservice.mapper.TimerMapper;
import com.waibaoservice.pojo.Timer;
import com.waibaoservice.utils.BeanUtils.SpringContextUtils;
import com.waibaoservice.utils.MqttUtils.MqttCallBackAdapter;
import com.waibaoservice.utils.MqttUtils.MqttUtils;
import com.waibaoservice.utils.WeiXinUtils.WeiXinRequestUtils;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author DJS
 * Date create 19:37 2023/3/14
 * Modified By DJS
 **/

public class DeviceTask implements Runnable{

    private static boolean loopCondition = true;
    TimerMapper mapper = SpringContextUtils.getBean(TimerMapper.class);

    @Override
    public void run() {
        System.out.println("MqttSubscriber start");
        while (loopCondition) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MqttUtils.subscribe(new MqttCallBackAdapter() {
                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    String msg = new String(mqttMessage.getPayload());
                    System.out.println("msg : " + msg);
                    Timer timer = mapper.selectTimerByDeviceId(msg);
                    if (timer != null) WeiXinRequestUtils.pushMessage(timer.getOpenid());
                }
            });
        }
    }
}
