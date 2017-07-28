package com.shizhanzhe.szzschool.utils;


import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by hasee on 2016/11/1.
 */
public class OkHttpDownloadJsonUtil {
    public interface onOkHttpDownloadListener {
        void onsendJson(String json);
    }

    public static void downloadJson(final Context context, final String path, final onOkHttpDownloadListener listener) {
        final Handler handler = new Handler();
//        final ProgressDialog dialog = new ProgressDialog(context);
//        dialog.setTitle("加载中请稍后");
//        dialog.setInverseBackgroundForced(true);
//        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        dialog.setMessage("正在加载...");
//        dialog.setIcon(R.drawable.load01);
//        dialog.show();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path).build();
        final Call call = client.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        InputStream is = response.body().byteStream();
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        byte[] b = new byte[1024];
                        int len = 0;
                        while ((len = is.read(b)) != -1) {
                            os.write(b, 0, len);
                        }
                        os.flush();
                        final String s = new String(os.toByteArray());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onsendJson(s);
                            }
                        });
                    }
                } catch (final Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (e.toString().contains("java.net.UnknownHostException")){
                                Toast.makeText(context,"网络异常，请检查网络后再试",Toast.LENGTH_LONG).show();;
                            }else{
                                Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();;
                            }
                        }
                    });
                }
            }
        }).start();
    }
}


