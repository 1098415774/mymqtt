package com.sh.doorbell.registerandactivate.service.impl;

import com.sh.doorbell.registerandactivate.dao.UserMapper;
import com.sh.doorbell.registerandactivate.entity.UserEntity;
import com.sh.doorbell.registerandactivate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public int insert(UserEntity entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public int update(UserEntity entity) {
        return mapper.updateSelective(entity);
    }

    @Override
    public List<UserEntity> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public UserEntity selectById(String id) {
        return mapper.selectById(id);
    }

    @Override
    public UserEntity selectByUserName(String username) {
        HashMap map = new HashMap();
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        map.put("entity",entity);
        List<UserEntity> list = mapper.selectByMap(map);
        return list.size() > 0 ? list.get(0) : null;
    }
}
