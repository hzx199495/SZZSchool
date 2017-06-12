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

    private AlertDialog dialog;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info!=null&&info.isConnected()){
            final Context c=context;
            switch (info.getType()) {
                case ConnectivityManager.TYPE_MOBILE:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("网络状态");
                    builder.setMessage("正在使用移动网络，是否切换网络");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent();
                            i.setAction(Settings.ACTION_SETTINGS);
                            c.startActivity(i);
                        }
                    });
                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            InternetReceiver.this.dialog.dismiss();
                        }

                    });
                    dialog = builder.create();
                    dialog.show();
                    break;
                case ConnectivityManager.TYPE_WIFI:
                    Toast.makeText(context, "正在使用wifi", 1).show();
                default:
                    break;
            }
        }else{
            Toast.makeText(context, "无网络连接，请检查后再试", 1).show();
        }
    }

}

