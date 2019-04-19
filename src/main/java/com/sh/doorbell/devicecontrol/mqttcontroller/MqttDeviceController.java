package com.sh.doorbell.devicecontrol.mqttcontroller;

import com.sh.base.cache.RedisCacheManager;
import com.sh.mqtt.annotation.MQTTRequestMapping;
import com.sh.mqtt.core.RequetMqttMessage;
import com.sh.mqtt.stereotype.MQTTController;
import org.springframework.beans.factory.annotation.Autowired;

@MQTTController
@MQTTRequestMapping("equip")
public class MqttDeviceController {
    @Autowired
    private RedisCacheManager redisCacheManager;

    @MQTTRequestMapping("data/#")
    public void getData(String data, RequetMqttMessage mqttMessage){
        String topic = mqttMessage.getTopic();
        redisCacheManager.set(topic,data);
    }
}
