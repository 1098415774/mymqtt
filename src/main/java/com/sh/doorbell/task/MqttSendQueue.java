package com.sh.doorbell.task;

import com.sh.mqtt.core.MqttMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class MqttSendQueue implements Runnable{

    private Logger logger = LogManager.getLogger(MqttSendQueue.class);

    private Map<String, MqttMessage> sendmap = new Hashtable<>();

    private MqttPahoMessageHandler mqttPahoMessageHandler;
    @Autowired
    private TaskQueue taskQueue;

    public void run() {
        while (true){
            try {
                if (sendmap.size() > 0){
                    for (Map.Entry<String, MqttMessage> entry : sendmap.entrySet()){
                        MqttMessage mqttMessage = entry.getValue();
                        Message<String> responsemessage = MessageBuilder.withPayload(mqttMessage.getMessage()).setHeader(MqttHeaders.TOPIC,mqttMessage.getTopic()).build();
                        mqttPahoMessageHandler.handleMessage(responsemessage);
                    }
                }
                Thread.sleep(500);
            }catch (Exception e){
                logger.error(e);
                e.printStackTrace();
            }

        }
    }

    public MqttPahoMessageHandler getMqttPahoMessageHandler() {
        return mqttPahoMessageHandler;
    }

    public void setMqttPahoMessageHandler(MqttPahoMessageHandler mqttPahoMessageHandler) {
        this.mqttPahoMessageHandler = mqttPahoMessageHandler;
    }

    public void addSendTask(String id, MqttMessage message, Task task){
        sendmap.put(id,message);
        taskQueue.removeTask(task);
    }

    public MqttMessage remove(String id){
        return sendmap.remove(id);
    }
}
