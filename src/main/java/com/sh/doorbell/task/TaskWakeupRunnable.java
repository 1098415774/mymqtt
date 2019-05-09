package com.sh.doorbell.task;

import java.util.concurrent.DelayQueue;

public class TaskWakeupRunnable implements Runnable{

    private DelayQueue delayQueue;

    public TaskWakeupRunnable(){
        delayQueue = new DelayQueue();
    }

    @Override
    public void run() {
        while (true){
            try {
                Task task = (Task) delayQueue.take();
                task.isaction(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addTask(Task task){
        delayQueue.offer(task);
    }
}
