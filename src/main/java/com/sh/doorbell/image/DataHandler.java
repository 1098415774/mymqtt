package com.sh.doorbell.image;

import com.sh.doorbell.handler.mqtt.MyAbstractMqttMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

public class DataHandler extends MyAbstractMqttMessageHandler {
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        System.out.println(message.getHeaders().get("mqtt_receivedTopic").toString());

    }
}
