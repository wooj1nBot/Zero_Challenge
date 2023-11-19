package com.release.zerochallenge;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Month implements Serializable {
    Map<String, Day> days;

    public Month(){
        days = new HashMap<>();
    }


    public Map<String, Day> getDays() {
        return days;
    }

    public void setDays(Map<String, Day> days) {
        this.days = days;
    }

    public void addDay(String d, Day day){
        days.put(d, day);
    }
}
