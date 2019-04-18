package com.sh.doorbell.handler;

import com.sh.mqtt.core.MqttMessage;

public abstract class MyAbstractHandler {
    protected MyAbstractHandler myAbstractHandler;

    public MyAbstractHandler getMyAbstractHandler() {
        return myAbstractHandler;
    }

    public void setMyAbstractHandler(MyAbstractHandler myAbstractHandler) {
        this.myAbstractHandler = myAbstractHandler;
    }

    public MqttMessage process(MqttMessage mqttMessage, Object... args) throws Exception{
        if (myAbstractHandler != null){
            mqttMessage = myAbstractHandler.process(mqttMessage, args);
        }
        return mqttMessage;
    }
}
