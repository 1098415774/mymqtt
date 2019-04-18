package com.sh.doorbell.registerandactivate.controller;

import com.sh.base.cache.LocalUserManager;
import com.sh.base.cache.RedisCacheManager;
import com.sh.base.result.ResultConstants;
import com.sh.base.result.ResultData;
import com.sh.base.utils.StringUtils;
import com.sh.doorbell.registerandactivate.RegisterContants;
import com.sh.doorbell.registerandactivate.entity.EquipInfoEntity;
import com.sh.doorbell.registerandactivate.entity.UserEntity;
import com.sh.doorbell.registerandactivate.form.UserForm;
import com.sh.doorbell.registerandactivate.service.EquipInfoService;
import com.sh.doorbell.registerandactivate.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    private static Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private EquipInfoService equipInfoService;
    @Autowired
    private LocalUserManager localUserManager;
    @Value("${token.length}")
    private Integer token_length;

    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public ResultData register(UserForm form){
        ResultData resultData = new ResultData();
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
        ResultData resultData = new ResultData();
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
            String token = StringUtils.getRandomString(token_length);
            localUserManager.setCurrentUser(token,user);
            resultData.setMsg(token);
            resultData.setState(ResultConstants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        return resultData;
    }

    @RequestMapping("getUserEquipInfo")
    @ResponseBody
    public ResultData getUserEquipInfo(String token){
        ResultData resultData = new ResultData();
        resultData.setState(ResultConstants.ERROR);
        try{
            if (StringUtils.isEmpty(token)){
                throw new IllegalArgumentException("token is null");
            }
            UserEntity user = localUserManager.getCurrentUser(token);
            List<EquipInfoEntity> resultlist = equipInfoService.selectByUserId(user.getId());
            resultData.setRows(resultlist);
            resultData.setState(ResultConstants.SUCCESS);
        }catch (Exception e){
            resultData.setMsg(e.getMessage());
            logger.error(e);
        }
        return resultData;
    }

}
