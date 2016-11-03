package com.shizhanzhe.szzschool.activity;

import android.app.Application;

import org.xutils.x;

/**
 * Created by hasee on 2016/10/31.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
