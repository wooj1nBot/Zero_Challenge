package com.release.zerochallenge;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ZeroUser implements Serializable {
    Map<String, Year> years;
    Map<String, Integer> achieve_reduce;
    Map<String, Integer> achieve_save;
    Map<String, Boolean> achieve;
    Map<String, Boolean> achieve_open;
    String uid;
    String name;

    int key;
    int achieve_count;
    boolean isRemoveAdd;

    UserIcons userIcons;

    public ZeroUser(){
        achieve_reduce = new HashMap<>();
        achieve_save = new HashMap<>();
        years = new HashMap<>();
        userIcons = new UserIcons();
        achieve = new HashMap<>();
        achieve_open = new HashMap<>();
        isRemoveAdd = false;
    }

    public Map<String, Year> getYears() {
        return years;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public UserIcons getUserIcons() {
        return userIcons;
    }

    public int getKey() {
        return key;
    }

    public void addYear(String y, Year year) {
        this.years.put(y, year);
    }

    public Map<String, Integer> getAchieve_reduce() {
        return achieve_reduce;
    }

    public Map<String, Integer> getAchieve_save() {
        return achieve_save;
    }

    public Map<String, Boolean> getAchieve() {
        return achieve;
    }
    public boolean getIsRemoveAdd(){
        return isRemoveAdd;
    }

    public Map<String, Boolean> getAchieve_open() {
        return achieve_open;
    }

    public void addReduce(int type){
        String t = String.valueOf(type);
        achieve_reduce.put(t, achieve_reduce.getOrDefault(t, 0) + 1);
    }
    public void addSave(int type){
        String t = String.valueOf(type);
        achieve_save.put(t, achieve_save.getOrDefault(t, 0) + 1);
    }

    public int getReduce(int type){
        return achieve_reduce.getOrDefault(String.valueOf(type), 0);
    }
    public int getSave(int type){
        return achieve_save.getOrDefault(String.valueOf(type), 0);
    }

    public int getAchieve_count() {
        return achieve_count;
    }
}
