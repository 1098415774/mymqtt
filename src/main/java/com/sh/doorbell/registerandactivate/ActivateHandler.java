package com.sh.doorbell.registerandactivate;

import com.alibaba.fastjson.JSONObject;
import com.sh.base.utils.StringUtils;
import com.sh.doorbell.handler.mqtt.MqttMessage;
import com.sh.doorbell.handler.mqtt.MyAbstractMqttMessageHandler;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;


public class ActivateHandler extends MyAbstractMqttMessageHandler {

    private Logger logger = LogManager.getLogger(ActivateHandler.class);

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            String msg = message.getPayload().toString();
            logger.info(msg);
            MqttMessage mqttMessage = null;
            mqttMessage = process(mqttMessage,msg);
            if (mqttMessage != null){
                if (StringUtils.isEmpty(mqttMessage.getTopic())){
                    logger.error("topic is null");
                }
                if (StringUtils.isEmpty(mqttMessage.getMessage())){
                    logger.error("message is null");
                }
                Message<String> result = MessageBuilder.withPayload(mqttMessage.getMessage()).setHeader(MqttHeaders.TOPIC,mqttMessage.getTopic()).build();
                while (true){
                    try {
                        mqttPahoMessageHandler.handleMessage(result);
                        break;
                    }catch (Exception e){

                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }

    }

}
