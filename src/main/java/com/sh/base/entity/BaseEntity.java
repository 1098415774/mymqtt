package com.sh.base.entity;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseEntity implements Serializable {
    //ID
    protected Integer id;
    //创建时间
    protected Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
