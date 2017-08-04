package com.shizhanzhe.szzschool.utils;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by hasee on 2016/12/21.
 */

public class InternetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info!=null&&info.isConnected()){
            switch (info.getType()) {
                case ConnectivityManager.TYPE_MOBILE:
                    Toast.makeText(context, "正在使用移动网络", Toast.LENGTH_SHORT).show();
                    break;
                case ConnectivityManager.TYPE_WIFI:
                    Toast.makeText(context, "正在使用wifi",  Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }else{
            Toast.makeText(context, "无网络连接，请检查后再试", Toast.LENGTH_SHORT).show();
        }
    }

}

