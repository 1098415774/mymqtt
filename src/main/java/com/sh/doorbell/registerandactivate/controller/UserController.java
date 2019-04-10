package com.sh.doorbell.registerandactivate.controller;

import com.sh.base.cache.LocalUserManager;
import com.sh.base.result.ResultConstants;
import com.sh.base.result.ResultData;
import com.sh.base.utils.StringUtils;
import com.sh.doorbell.registerandactivate.RegisterContants;
import com.sh.doorbell.registerandactivate.entity.UserEntity;
import com.sh.doorbell.registerandactivate.form.UserForm;
import com.sh.doorbell.registerandactivate.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("user")
public class UserController {

    private static Logger logger = LogManager.getLogger(UserController.class);

    private ResultData resultData;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public ResultData register(UserForm form){
        resultData = new ResultData();
        resultData.setState(ResultConstants.ERROR);
        try {
            UserEntity entity = new UserEntity();
            form.FormToEntity(entity);
            if (StringUtils.isEmpty(entity.getPassword()) || StringUtils.isEmpty(entity.getUsername())){
                throw new IllegalArgumentException("username or password is null");
            }
            userService.insert(entity);
            resultData.setState(ResultConstants.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e);
            resultData.setMsg(e.getMessage());
        }
        return resultData;
    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public ResultData login(UserForm form){
        resultData = new ResultData();
        resultData.setState(ResultConstants.ERROR);
        try {
            UserEntity user = null;
            if (StringUtils.isEmpty(form.getUsername()) || StringUtils.isEmpty(form.getPassword())){
                resultData.setMsg(RegisterContants.NULL_INFO);
                throw new IllegalArgumentException("info is null!");
            }
            user = userService.selectByUserName(form.getUsername());
            if (user == null){
                resultData.setMsg(RegisterContants.USER_NO_EXITS);
                throw new NullPointerException("user not exit!");
            }
            if(!user.getPassword().equals(form.getPassword())){
                resultData.setMsg(RegisterContants.PASSWORD_MISTAKE);
                throw new IllegalAccessException("password mistake!");
            }
            LocalUserManager.getInstance().setCurrentUser(user);
            resultData.setState(ResultConstants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        return resultData;
    }


}
