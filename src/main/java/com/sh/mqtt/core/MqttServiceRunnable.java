package com.sh.mqtt.core;

import com.sh.doorbell.handler.mqtt.MqttMessage;
import com.sh.mqtt.core.method.MqttHandlerMethod;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.lang.reflect.InvocationTargetException;

public class MqttServiceRunnable implements Runnable{

    private MqttSendListener listener;

    private MqttHandlerMethod mqttHandlerMethod;

    private Message message;

    public MqttServiceRunnable(MqttHandlerMethod mqttHandlerMethod, Message<?> message){
        this.mqttHandlerMethod = mqttHandlerMethod;
        this.message = message;
    }

    @Override
    public void run() {
        if (mqttHandlerMethod == null){
            return;
        }
        String msg = message.getPayload().toString();
        try {
            Object result = mqttHandlerMethod.invoke(msg);
            if (result == null || !mqttHandlerMethod.isResponse()){
                return;
            }
            MqttMessage mqttMessage = mqttHandlerMethod.getResponsemsg();
            mqttMessage.setMessage((String) result);
            listener.response(mqttMessage);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public MqttSendListener getListener() {
        return listener;
    }

    public void setListener(MqttSendListener listener) {
        this.listener = listener;
    }
}
