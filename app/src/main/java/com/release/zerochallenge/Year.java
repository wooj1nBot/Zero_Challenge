package com.release.zerochallenge;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Year implements Serializable {

    Map<String, Month> months;

    public Year(){
        months = new HashMap<>();
    }

    public Map<String, Month> getMonths() {
        return months;
    }

    public void setMonths(Map<String, Month> months) {
        this.months = months;
    }


    public void addMonth(String m, Month month){
        months.put(m, month);
    }
}
