package com.release.zerochallenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserIcons {
    ArrayList<String> reduceIcon;
    ArrayList<String> saveIcon;
    ArrayList<String> purchaseIcon;

    public UserIcons(){
        reduceIcon = new ArrayList<>();
        saveIcon = new ArrayList<>();
        purchaseIcon = new ArrayList<>();

        for(int i = 0; i < Icons.REDUCE_ICON.length; i++){
            reduceIcon.add(Icons.REDUCE_ICON[i][0].id);
        }
        for(int i = 0; i < Icons.SAVE_ICON.length; i++){
            saveIcon.add(Icons.SAVE_ICON[i][0].id);
        }
    }

    public ArrayList<String> getReduceIcon() {
        return reduceIcon;
    }

    public ArrayList<String> getSaveIcon() {
        return saveIcon;
    }

    public ArrayList<String> getPurchaseIcon() {
        return purchaseIcon;
    }

    public Icons.Icon getIcon(int type, boolean isReduce){
        if(isReduce){
            String key = reduceIcon.get(type);
            for(int i = 0; i < Icons.REDUCE_ICON[type].length; i++){
                if(Icons.REDUCE_ICON[type][i].id.equals(key)){
                    return Icons.REDUCE_ICON[type][i];
                }
            }
            return Icons.REDUCE_ICON[type][0];
        }else{
            String key = saveIcon.get(type);
            for(int i = 0; i < Icons.SAVE_ICON[type].length; i++){
                if(Icons.SAVE_ICON[type][i].id.equals(key)){
                    return Icons.SAVE_ICON[type][i];
                }
            }
            return Icons.SAVE_ICON[type][0];
        }
    }
}
