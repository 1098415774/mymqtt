package com.sh.doorbell.registerandactivate.service.impl;

import com.sh.doorbell.registerandactivate.dao.EquipInfoMapper;
import com.sh.doorbell.registerandactivate.entity.EquipInfoEntity;
import com.sh.doorbell.registerandactivate.service.EquipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int update(EquipInfoEntity record) {
        return mapper.updateByPrimaryKeySelective(record);
    }
}
