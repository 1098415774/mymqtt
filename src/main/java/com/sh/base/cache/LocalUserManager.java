package com.sh.base.cache;

import com.sh.doorbell.registerandactivate.entity.UserEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class LocalUserManager {

    private static LocalUserManager localUserManager;

    private static HttpServletRequest request;

    private LocalUserManager(){
        initialize();
    }

    public static LocalUserManager getInstance(){
        if (localUserManager == null){
            localUserManager = new LocalUserManager();
        }
        return localUserManager;
    }

    private void initialize() {
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static UserEntity getCurrentUser(){
        UserEntity cuser = (UserEntity) request.getSession().getAttribute("CUSER");
        return cuser;
    }

    public static void setCurrentUser(UserEntity cuser){
        request.getSession().setAttribute("CUSER",cuser);
    }

}
