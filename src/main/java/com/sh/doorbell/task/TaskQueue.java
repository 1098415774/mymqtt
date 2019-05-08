package com.sh.doorbell.task;

import com.sh.doorbell.devicecontrol.common.EquipAction;
import com.sh.doorbell.devicecontrol.common.SmartAction;
import com.sh.doorbell.task.listener.ListenerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;

import java.util.ArrayList;
import java.util.List;

public class TaskQueue {
    @Autowired
    private MqttPahoMessageHandler mqttPahoMessageHandler;
    @Autowired
    private MqttSendQueue mqttSendQueue;

    List myqueue = new ArrayList();
    private ListenerManager listenerManager = new ListenerManager();

    public void addTask(Task task){
        task.setListenerManager(listenerManager);
        task.setMqttPahoMessageHandler(mqttPahoMessageHandler);
        task.setMqttSendQueue(mqttSendQueue);
        task.init();
        myqueue.add(task);
    }

    public boolean removeTask(Task task){
        int i = task.getNum() - 1;
        if (i > 0){
            task.setNum(i);
            return true;
        }
        SmartAction smartAction = task.getSmartAction();
        for (EquipAction equipAction : smartAction.getIfactions()){
            listenerManager.remove(equipAction.getEquipId());
        }
        return myqueue.remove(task);
    }

    public void tryToExecTask(String id, String data){
        listenerManager.calltem(id,data);
    }
}
