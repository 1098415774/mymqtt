package com.sh.doorbell.devicecontrol.controller;


import com.sh.base.cache.LocalUserManager;
import com.sh.base.cache.RedisCacheManager;
import com.sh.base.result.ResultConstants;
import com.sh.base.result.ResultData;
import com.sh.doorbell.devicecontrol.service.DeviceControlService;
import com.sh.doorbell.registerandactivate.entity.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("device")
public class DeviceController {
    private Logger logger = LogManager.getLogger(DeviceController.class);

    private ResultData resultData;
    @Autowired
    private DeviceControlService deviceControlService;

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



}
