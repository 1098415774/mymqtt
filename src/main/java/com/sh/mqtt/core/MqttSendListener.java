package com.sh.mqtt.core;

public interface MqttSendListener {

    void response(MqttMessage mqttMessage);
}
