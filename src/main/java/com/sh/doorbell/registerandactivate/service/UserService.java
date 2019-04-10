package com.sh.doorbell.registerandactivate.service;

import com.sh.doorbell.registerandactivate.entity.UserEntity;

import java.util.List;

public interface UserService {

    int insert(UserEntity entity);

    int update(UserEntity entity);

    List<UserEntity> selectAll();

    UserEntity selectById(String id);

    UserEntity selectByUserName(String username);

}
