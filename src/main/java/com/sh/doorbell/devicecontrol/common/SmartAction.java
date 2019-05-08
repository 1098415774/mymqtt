package com.sh.doorbell.devicecontrol.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SmartAction implements Serializable {

    private List<EquipAction> ifactions = new LinkedList<>();

    private List<EquipAction> thenactions = new LinkedList<>();

    private String smartid;

    private String smartname;

    private String time;

    private int num = 0;

    private int iconid;

    public String getSmartid() {
        return smartid;
    }

    public void setSmartid(String smartid) {
        this.smartid = smartid;
    }

    public String getSmartname() {
        return smartname;
    }

    public void setSmartname(String smartname) {
        this.smartname = smartname;
    }

    public int getIconid() {
        return iconid;
    }

    public void setIconid(int iconid) {
        this.iconid = iconid;
    }

    public List<EquipAction> getIfactions() {
        return ifactions;
    }

    public void setIfactions(List<EquipAction> ifactions) {
        this.ifactions = ifactions;
    }

    public List<EquipAction> getThenactions() {
        return thenactions;
    }

    public void setThenactions(List<EquipAction> thenactions) {
        this.thenactions = thenactions;
    }

    public void addIfaction(EquipAction action){
        this.ifactions.add(action);
    }

    public void removeIfaction(EquipAction action){
        this.ifactions.remove(action);
    }

    public void addThenaction(EquipAction action){
        this.thenactions.add(action);
    }

    public void removeThenaction(EquipAction action){
        this.thenactions.remove(action);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
