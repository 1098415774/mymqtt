package com.sh.doorbell.task.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListenerManager {

    private HashMap<String, List<SensorListener>> temTask = new HashMap<>();

    public void add(String id, SensorListener sensorListener){
        List<SensorListener> listeners = temTask.get(id);
        if (listeners == null){
            listeners = new ArrayList<>();
        }
        listeners.add(sensorListener);
        temTask.put(id,listeners);
    }

    public void remove(String id){
        temTask.remove(id);
    }

    public void calltem(String id, String data){
        List<SensorListener> listeners = temTask.get(id);
        if (listeners == null){
            return;
        }
        for (SensorListener sensorListener : listeners){
            sensorListener.call(id,data);
        }
    }
}
