package com.sh.mqtt.core.analysis.handler;

import com.sh.mqtt.core.RequetMqttMessage;
import com.sh.mqtt.core.method.MqttHandlerMethod;

import java.text.ParseException;

public interface MqttPreparserHandler {

    String parser(String msg, RequetMqttMessage requetMqttMessage, Object mqttHandlerMethod) throws ParseException;
}
