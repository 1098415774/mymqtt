package com.sh.doorbell.registerandactivate.service.impl;

import com.sh.doorbell.registerandactivate.dao.EquipInfoMapper;
import com.sh.doorbell.registerandactivate.entity.EquipInfoEntity;
import com.sh.doorbell.registerandactivate.service.EquipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class EquipInfoServiceImpl implements EquipInfoService {
    @Autowired
    private EquipInfoMapper mapper;

    @Override
    public int insert(EquipInfoEntity record) {
        return mapper.insertSelective(record);
    }

    @Override
    public EquipInfoEntity selectById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<EquipInfoEntity> selectByUserId(int userid) {
        HashMap<String,Object> map = new HashMap<>();
        EquipInfoEntity entity = new EquipInfoEntity();
        entity.setUserId(String.valueOf(userid));
        map.put("entity",entity);
        return mapper.selectByMap(map);
    }

    @Override
    public int update(EquipInfoEntity record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<EquipInfoEntity> selectByTypeAndUserId(Integer type, String userId) {
        HashMap<String,Object> map = new HashMap<>();
        EquipInfoEntity entity = new EquipInfoEntity();
        entity.setType(type);
        entity.setUserId(userId);
        map.put("entity",entity);
        return mapper.selectByMap(map);
    }
}
