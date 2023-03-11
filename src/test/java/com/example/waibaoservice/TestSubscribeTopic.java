package com.example.waibaoservice;

import com.waibaoservice.utils.MqttUtils.MqttCallBackAdapter;
import com.waibaoservice.utils.MqttUtils.MqttUtils;
import org.eclipse.paho.client.mqttv3.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author DJS
 * Date create 14:28 2023/2/22
 * Modified By DJS
 **/
@SpringBootTest(classes = TestSubscribeTopic.class)
public class TestSubscribeTopic {

    @Test
    public void testMqtt() {
        MqttUtils.setSubscribeId("cancelTimer");
        MqttUtils.subscribe(new MqttCallBackAdapter() {
            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                System.out.println(mqttMessage);
            }
        });
    }

    static void test() {
        String broker = "tcp://test.ranye-iot.net:1883";
        String topic = "MA/test";
        String clientid = "DJS_PYM_subscribe";
        int qos = 0;

        try {
            MqttClient client = new MqttClient(broker, clientid);
            // 连接参数
            MqttConnectOptions options = new MqttConnectOptions();
            options.setConnectionTimeout(60);
            options.setKeepAliveInterval(60);
            // 设置回调
            client.setCallback(new MqttCallback() {

                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost: " + cause.getMessage());
                }

                public void messageArrived(String topic, MqttMessage message) {
                    System.out.println("topic: " + topic);
                    System.out.println("Qos: " + message.getQos());
                    System.out.println("message content: " + new String(message.getPayload()));
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }

            });
            client.connect(options);
            client.subscribe(topic, qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MqttUtils.setSubscribeId("cancelTimer");
        MqttUtils.subscribe(new MqttCallBackAdapter(){
            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                System.out.println(new String(mqttMessage.getPayload()));
            }
        });
    }
}
