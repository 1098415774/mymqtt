package com.sh.doorbell.registerandactivate;

import com.sh.base.utils.SpringUtil;
import com.sh.base.utils.StringUtils;
import com.sh.doorbell.handler.MyAbstractHandler;
import com.sh.doorbell.handler.mqtt.MqttMessage;
import com.sh.doorbell.registerandactivate.entity.EquipInfoEntity;
import com.sh.doorbell.registerandactivate.service.EquipInfoService;
import com.sh.doorbell.registerandactivate.service.impl.EquipInfoServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class ActivateDoorbellHandler extends MyAbstractHandler {

    private Logger logger = LogManager.getLogger(ActivateDoorbellHandler.class);
    @Autowired
    private EquipInfoService equipInfoService;

    private String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public ActivateDoorbellHandler(){
        System.out.println("ActivateDoorbellHandler create");
    }


    @Override
    public MqttMessage process(MqttMessage mqttMessage, Object... args) throws Exception {
        if (mqttMessage == null){
            mqttMessage = new MqttMessage();
        }
        if (args.length < 1){
            throw new IllegalArgumentException("not message!");
        }
        String msg = ((String) args[0]).trim();
        String eid = msg.substring(msg.indexOf("EID:") + 4,msg.indexOf("UID:"));
        String uid = msg.substring(msg.indexOf("UID:") + 4,msg.length());
        if (StringUtils.isEmpty(eid) || StringUtils.isEmpty(uid)){
            throw new IllegalArgumentException();
        }
        if (equipInfoService == null){
            equipInfoService = SpringUtil.getBean(EquipInfoServiceImpl.class);
        }
        EquipInfoEntity equipInfoEntity = new EquipInfoEntity();
        equipInfoEntity.setId(Integer.parseInt(eid));
        equipInfoEntity.setUserId(uid);
        equipInfoEntity.setCreateTime(new Date());
        try {
            equipInfoService.insert(equipInfoEntity);
        }catch (Exception e){
            equipInfoService.update(equipInfoEntity);
        }

        mqttMessage.setTopic(this.topic + "/" + eid);
        mqttMessage.setMessage("OK");
        return super.process(mqttMessage, args);
    }



    //    public void process(String topic, String msg){
//        process(topic,msg);
//    }

}
