package com.sh.doorbell.task;

import com.sh.base.utils.StringUtils;
import com.sh.doorbell.devicecontrol.common.EquipAction;
import com.sh.doorbell.devicecontrol.common.SmartAction;
import com.sh.doorbell.task.listener.ListenerManager;
import com.sh.doorbell.task.listener.SensorListener;
import com.sh.doorbell.task.util.EquipActionUtil;
import com.sh.mqtt.core.MqttMessage;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Task implements Delayed {

    private SmartAction smartAction;

    private boolean[] ifarray;

    private long delaytime;

    private MqttPahoMessageHandler mqttPahoMessageHandler;

    private ListenerManager listenerManager;

    private MqttSendQueue mqttSendQueue;

    private int num = Integer.MAX_VALUE;

    public Task(SmartAction smartAction){
        this.smartAction = smartAction;
    }

    public void init(){
        if (smartAction == null){
            return;
        }
        List<EquipAction> ifactions = smartAction.getIfactions();
        ifarray = new boolean[ifactions.size() + 1];
        ifarray[ifarray.length - 1] = true;
        for (int i = 0; i < ifactions.size(); i++){
            final int index = i;
            EquipAction equipAction = ifactions.get(i);
            SensorListener listener = new SensorListener() {
                @Override
                public void call(String id, String data) {
                    switch (equipAction.getType()){
                        case 1:
                            if (equipAction.getActionid().equals("100")){
                                if (Integer.parseInt(data) > Integer.parseInt(equipAction.getData())){
                                    ifarray[index] = true;
                                }else {
                                    ifarray[index] = false;
                                }
                            }else if (equipAction.getActionid().equals("101")){
                                if (Integer.parseInt(data) < Integer.parseInt(equipAction.getData())){
                                    ifarray[index] = true;
                                }else {
                                    ifarray[index] = false;
                                }
                            }
                            break;
                    }
                    execute();
                }
            };
            if (listenerManager != null){
                listenerManager.add(equipAction.getEquipId(),listener);
            }
        }
    }

    public void execute(){
        if (ifarray == null || ifarray.length < 1 || mqttPahoMessageHandler == null){
            return;
        }
        for (boolean ifexec : ifarray){
            if (!ifexec){
                return;
            }
        }
        for (EquipAction equipAction : smartAction.getThenactions()){
            String equipid = equipAction.getEquipId();
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setTopic("equip/" + equipid);
            String msg = null;
            try {
                msg = EquipActionUtil.getEquipOrder(equipid,equipAction.getActionid());
                if (StringUtils.isEmpty(msg)){
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder("c");
                stringBuilder.append(msg);
                stringBuilder.append("rn");
                mqttMessage.setMessage(stringBuilder.toString());
                mqttSendQueue.addSendTask(equipid,mqttMessage,this);
            } catch (IOException e) {
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

    public ListenerManager getListenerManager() {
        return listenerManager;
    }

    public void setListenerManager(ListenerManager listenerManager) {
        this.listenerManager = listenerManager;
    }

    public MqttSendQueue getMqttSendQueue() {
        return mqttSendQueue;
    }

    public void setMqttSendQueue(MqttSendQueue mqttSendQueue) {
        this.mqttSendQueue = mqttSendQueue;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public SmartAction getSmartAction() {
        return smartAction;
    }

    public void setSmartAction(SmartAction smartAction) {
        this.smartAction = smartAction;
    }

    public void isaction(boolean isaction){
        if (ifarray != null && ifarray.length > 0){
            ifarray[ifarray.length] = isaction;
        }
    }

    public void setDelaytime(long delaytime){
        this.delaytime = TimeUnit.MILLISECONDS.convert(delaytime,TimeUnit.SECONDS) + System.currentTimeMillis();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = delaytime - System.currentTimeMillis();
        return unit.convert(diff,TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (delaytime > o.getDelay(TimeUnit.MILLISECONDS)) ? 1 : (delaytime < o.getDelay(TimeUnit.MILLISECONDS) ? -1 : 0);
    }
}
