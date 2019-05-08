package com.sh.base.cache;

import com.sh.doorbell.registerandactivate.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Component
@PropertySource("classpath:application.properties")
public class LocalUserManager {

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Value("${redis.cache.overtime}")
    private Integer overtime;

    public UserEntity getCurrentUser(String token){
        UserEntity entity = (UserEntity) redisCacheManager.get(token);
        if (entity != null){
            redisCacheManager.expire(token,overtime);
        }
        return entity;
    }

    public void setCurrentUser(String token, UserEntity cuser){
        if (redisCacheManager.set(token,cuser)){
            redisCacheManager.expire(token,overtime);
        }
    }

}
