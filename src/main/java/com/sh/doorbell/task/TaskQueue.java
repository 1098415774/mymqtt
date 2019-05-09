package com.sh.doorbell.task;

import com.sh.doorbell.devicecontrol.common.EquipAction;
import com.sh.doorbell.devicecontrol.common.SmartAction;
import com.sh.doorbell.task.listener.ListenerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskQueue {
    @Autowired
    private MqttPahoMessageHandler mqttPahoMessageHandler;
    @Autowired
    private MqttSendQueue mqttSendQueue;
    @Autowired
    private TaskWakeupRunnable runnable;

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
            task.isaction(false);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE,calendar.get(Calendar.DATE) + 1);
            calendar.set(Calendar.HOUR,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            task.setDelaytime((calendar.getTimeInMillis() - System.currentTimeMillis())/1000);
            runnable.addTask(task);
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
