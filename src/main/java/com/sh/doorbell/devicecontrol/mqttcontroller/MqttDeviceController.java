package com.sh.doorbell.devicecontrol.mqttcontroller;

import com.sh.base.cache.RedisCacheManager;
import com.sh.doorbell.task.MqttSendQueue;
import com.sh.doorbell.task.TaskQueue;
import com.sh.mqtt.annotation.MQTTRequestMapping;
import com.sh.mqtt.core.RequetMqttMessage;
import com.sh.mqtt.stereotype.MQTTController;
import org.springframework.beans.factory.annotation.Autowired;

@MQTTController
@MQTTRequestMapping("equip")
public class MqttDeviceController {
    @Autowired
    private RedisCacheManager redisCacheManager;
    @Autowired
    private TaskQueue taskQueue;
    @Autowired
    private MqttSendQueue mqttSendQueue;

    @MQTTRequestMapping("data/#")
    public void getData(String data, RequetMqttMessage mqttMessage){
        String topic = mqttMessage.getTopic();
        if (data.substring(0,4).equalsIgnoreCase("TEM:")){
            data = String.valueOf(((int)data.charAt(data.length() - 1)));
        }
        redisCacheManager.set(topic,data);
        String id = topic.substring(topic.lastIndexOf('/') + 1, topic.length());
        taskQueue.tryToExecTask(id,data);
    }
    @MQTTRequestMapping("ctrldata/#")
    public void getCtrlData(String data, RequetMqttMessage mqttMessage){
        String topic = mqttMessage.getTopic();
    }

    @MQTTRequestMapping("responsedata/#")
    public void response(String data, RequetMqttMessage mqttMessage){
        if (data.equalsIgnoreCase("MQTTGET")){
            String topic = mqttMessage.getTopic().trim();
            String id = topic.substring(topic.lastIndexOf('/'),topic.length());
            mqttSendQueue.remove(id);
        }
    }
}
