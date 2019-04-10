package com.sh.doorbell.devicecontrol.service;

import com.sh.doorbell.handler.mqtt.MyAbstractMqttMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

public interface DeviceControlService {

    void open(String deviceId);

}
