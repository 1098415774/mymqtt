package com.sh.doorbell.devicecontrol.controller;


import com.sh.base.cache.LocalUserManager;
import com.sh.base.cache.RedisCacheManager;
import com.sh.base.result.ResultConstants;
import com.sh.base.result.ResultData;
import com.sh.base.utils.StringUtils;
import com.sh.doorbell.devicecontrol.common.EquipAction;
import com.sh.doorbell.devicecontrol.common.SmartAction;
import com.sh.doorbell.devicecontrol.service.DeviceControlService;
import com.sh.doorbell.registerandactivate.entity.EquipInfoEntity;
import com.sh.doorbell.registerandactivate.entity.UserEntity;
import com.sh.doorbell.registerandactivate.service.EquipInfoService;
import com.sh.doorbell.task.Task;
import com.sh.doorbell.task.TaskQueue;
import com.sh.mqtt.core.MqttMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("device")
public class DeviceController {
    private Logger logger = LogManager.getLogger(DeviceController.class);

    private ResultData resultData;
    @Autowired
    private DeviceControlService deviceControlService;
    @Autowired
    protected MqttPahoMessageHandler mqttPahoMessageHandler;;

    @Autowired
    private RedisCacheManager redisCacheManager;
    @Autowired
    private TaskQueue taskQueue;

//    @RequestMapping("open")
//    @ResponseBody
//    public ResultData open(String eid){
//        resultData = new ResultData();
//        resultData.setState(ResultConstants.ERROR);
//        try {
//            UserEntity user = LocalUserManager.getInstance().getCurrentUser();
//            if (user == null){
//                throw new Exception("user is null");
//            }
//            deviceControlService.open(eid);
//            resultData.setState(ResultConstants.SUCCESS);
//        }catch (Exception e){
//            logger.error(e);
//            resultData.setMsg(e.getMessage());
//        }
//        return resultData;
//    }
//
//    @RequestMapping("getdata")
//    @ResponseBody
//    public ResultData getEquipData(String eid){
//        resultData = new ResultData();
//        resultData.setState(ResultConstants.ERROR);
//        try {
//            UserEntity user = LocalUserManager.getInstance().getCurrentUser();
//            String data = (String) redisCacheManager.get("E" + eid + "U" + user.getId());
//            resultData.setMsg(data);
//            resultData.setState(ResultConstants.SUCCESS);
//        }catch (Exception e){
//            logger.error(e);
//            resultData.setMsg(e.getMessage());
//        }
//        return resultData;
//    }

    @RequestMapping("testdata")
    @ResponseBody
    public String putTestData(String data){
        if (StringUtils.isNotEmpty(data)){
            redisCacheManager.set("testdata",data);
        }
        return "";
    }
    @RequestMapping("gettestdata")
    @ResponseBody
    public String getTestData(){
        String data = "";
        data = (String) redisCacheManager.get("testdata");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        MqttMessage mqttMessage = new MqttMessage();
                        mqttMessage.setTopic("equip/11111");
                        char[] aa = {0x30,0x03,0x72,0xc7,0x00,0x34,0x0f,0x01,0x15,0x01,0x5f,0x00,0x26,0x00,0x1c,0x00,0x26,0x00,0x45,0x01,0x24,0x00,0x00,0x00,0x1f,0x00,0xff,0xff,0xff,0xff,0xff,0xff,0xff,0xff,0xff,0xff,0xff,0xff,0x01,0x21,0x12,0x21,0x22,0x12,0x21,0x12,0x12,0x11,0x11,0x21,0x11,0x22,0x22,0x12,0x21,0x11,0x22,0x22,0x22,0x22,0x11,0x11,0x13,0x01,0x21,0x12,0x21,0x22,0x12,0x21,0x12,0x12,0x11,0x11,0x21,0x11,0x22,0x22,0x12,0x21,0x11,0x22,0x22,0x22,0x22,0x11,0x11,0x14,0xf0,0x76,0x3f,0x30};
                        StringBuilder stringBuilder = new StringBuilder("c");
                        for (char a : aa){
                            stringBuilder.append(String.valueOf(a));
                        }
                        stringBuilder.append("rn");
                        mqttMessage.setMessage(stringBuilder.toString());
                        Message<String> responsemessage = MessageBuilder.withPayload(mqttMessage.getMessage()).setHeader(MqttHeaders.TOPIC,mqttMessage.getTopic()).build();
                        if (mqttPahoMessageHandler != null){
                            mqttPahoMessageHandler.handleMessage(responsemessage);
                        }
                        Thread.sleep(500);
                    }catch (Exception e){

                    }
                }
            }
        });
        thread.start();
        return data;
    }

    @RequestMapping(value = "customPlan",method = RequestMethod.POST)
    @ResponseBody
    public ResultData customPlan(@RequestBody SmartAction smartAction){
        resultData = new ResultData();
        resultData.setMsg(ResultConstants.ERROR);
        Task task = null;
        try {
            if (smartAction == null){
                throw new IllegalArgumentException("smartaction is null");
            }
            task = new Task(smartAction);
            if (smartAction.getNum() != 0){
                task.setNum(smartAction.getNum());
            }
            taskQueue.addTask(task);
            resultData.setState(ResultConstants.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e);
        }
        return resultData;
    }


}
