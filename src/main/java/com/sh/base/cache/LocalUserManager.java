package com.sh.base.cache;

import com.sh.doorbell.registerandactivate.entity.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Component
public class LocalUserManager {

    private static LocalUserManager localUserManager;

    private static HashMap<String, UserEntity> usermap;

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
        usermap = new HashMap<>();
    }

    public static UserEntity getCurrentUser(String token){
        return usermap.get(token);
    }

    public static void setCurrentUser(String token, UserEntity cuser){
        usermap.put(token, cuser);
    }

}
