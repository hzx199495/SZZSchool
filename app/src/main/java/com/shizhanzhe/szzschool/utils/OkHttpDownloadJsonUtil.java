package com.shizhanzhe.szzschool.utils;


import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
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
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path).build();
        final Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("____",e.toString());
                        if (e.toString().contains("SocketTimeoutException")){
                            listener.onsendJson("1");
                        } else if (e.toString().contains("UnknownHostException")) {
                            listener.onsendJson("0");
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    final String str = response.body().string();
                    handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onsendJson(str);
                            }
                        });
            }
        });
    }
}


