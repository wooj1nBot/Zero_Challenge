package com.release.zerochallenge;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Day implements Serializable {

    HashMap<String, Boolean> reduce;
    HashMap<String, Boolean> save;
    int saveMoney;
    int year;
    int month;
    int day;

    public Day(){}

    public Day(int year, int month, int day){
        reduce = new HashMap<>();
        save = new HashMap<>();
        this.day = day;
        this.year = year;
        this.month = month;
    }


    public HashMap<String, Boolean> getReduce() {
        return reduce;
    }

    public HashMap<String, Boolean> getSave() {
        return save;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }

    public int getSaveMoney() {
        return saveMoney;
    }

    public void setSaveMoney(int saveMoney) {
        this.saveMoney = saveMoney;
    }

    public void setReduce(HashMap<String, Boolean> reduce) {
        this.reduce = reduce;
    }

    public void setSave(HashMap<String, Boolean> save) {
        this.save = save;
    }


    @NonNull
    @Override
    public String toString() {
        return year + "/" + month + "/" + day;
    }
}
