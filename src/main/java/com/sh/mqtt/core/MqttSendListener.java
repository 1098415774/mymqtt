package com.sh.mqtt.core;

import com.sh.doorbell.handler.mqtt.MqttMessage;

public interface MqttSendListener {

    void response(MqttMessage mqttMessage);
}
