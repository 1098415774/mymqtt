package com.sh.doorbell.devicecontrol.dao;


import com.sh.doorbell.devicecontrol.entity.EquipTypeEntity;

public interface EquipTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table equip_type_info
     *
     * @mbggenerated
     */
    int insert(EquipTypeEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table equip_type_info
     *
     * @mbggenerated
     */
    int insertSelective(EquipTypeEntity record);

    EquipTypeEntity selectById(Integer id);
}