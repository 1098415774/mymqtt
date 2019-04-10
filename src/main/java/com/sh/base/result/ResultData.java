package com.sh.base.result;

import java.util.List;

/*
* 回传类
* */
public class ResultData {
    /*
    * 状态标志位
    * error:ResultConstant.ERROR
    * success:ResultConstant.SUCCESS
    * */
    private String state;
    //回传信息
    private String msg;
    //查询结果
    private List rows ;

    private int total;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

