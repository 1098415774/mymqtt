package com.sh.mqtt.core.analysis.handler;

import com.sh.mqtt.core.method.MqttHandlerMethod;

import java.text.ParseException;

public abstract class JsonPackagingHandler {

    public abstract Object parser(String msg, Class<?> parameterType) throws ParseException;
}
