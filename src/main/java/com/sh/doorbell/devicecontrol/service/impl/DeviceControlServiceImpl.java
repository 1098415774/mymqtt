package com.sh.doorbell.devicecontrol.service.impl;

import com.sh.doorbell.devicecontrol.service.DeviceControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class DeviceControlServiceImpl implements DeviceControlService {

    @Autowired
    private MqttPahoMessageHandler mqttPahoMessageHandler;

    @Override
    public void open(String deviceId) {
        Message<String> message = MessageBuilder.withPayload("open").setHeader(MqttHeaders.TOPIC,"ctrl/" + deviceId).build();
        mqttPahoMessageHandler.handleMessage(message);
    }
}
