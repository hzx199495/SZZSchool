package com.shizhanzhe.szzschool.utils;

import java.io.Serializable;

/**
 * Created by zz9527 on 2017/12/27.
 */

public class ItemModel implements Serializable {
//    //左上角三角图标
//    public static final int ONE = 1001;
    //textview布局
    public static final int TWO = 1;
    //edittext布局
    public static final int THREE = 2;
    public int type;
    public Object data;
//    //是否免费的标志
//    public boolean isFree;
    public ItemModel(int type, Object data) {
        this.type = type;
        this.data = data;
    }
}