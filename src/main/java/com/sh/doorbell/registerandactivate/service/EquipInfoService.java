package com.sh.doorbell.registerandactivate.service;

import com.sh.doorbell.registerandactivate.entity.EquipInfoEntity;

import java.util.List;

public interface EquipInfoService {

    int insert(EquipInfoEntity record);

    EquipInfoEntity selectById(Integer id);

    List<EquipInfoEntity> selectByIds(List ids);

    List<EquipInfoEntity> selectByUserId(int userid);

    int update(EquipInfoEntity record);

    List<EquipInfoEntity> selectByTypeAndUserId(Integer type, String userId);
}
