package com.shizhanzhe.szzschool.utils;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hasee on 2016/11/29.
 */
public class SerializableHashMap implements Serializable {

    private HashMap<String, List<String>> map;

    public HashMap<String, List<String>> getMap() {
        return map;
    }

    public void setMap(HashMap<String, List<String>> map) {
        this.map = map;
    }
}
