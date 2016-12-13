package com.shizhanzhe.szzschool.pay;


import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by hasee on 2016/11/1.
 */
public class OkHttpPostOrder {
    public  long order;
    public interface onOkHttpDownloadListener {
        void onsendJson(String json);
    }

    public static void postOrder() {
//        final Handler handler = new Handler();
//        final ProgressDialog dialog = new ProgressDialog(context);
//        dialog.setTitle("加载中请稍后");
//        dialog.setInverseBackgroundForced(true);
//        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        dialog.setMessage("正在加载...");
//        dialog.setIcon(R.drawable.load01);
//        dialog.show();
        OkHttpClient client = new OkHttpClient();
        RequestBody body=new FormBody.Builder()
                .add("uid","849").add("price","0.01")
                .build();
//在构建Request对象时，调用post方法，传入RequestBody对象
        Request request=new Request.Builder()
                .url("http://2.huobox.com/index.php?m=member.save_bone&pc=1")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("fail",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                Log.i("success",s);
                String a[] = s.split("</script>");
                Gson gson = new Gson();
                PayBean payBean = gson.fromJson(a[1], PayBean.class);
                long order = payBean.getOrder();


            }
        });


//        Request request = new Request.Builder().url(path).build();
//        final Call call = client.newCall(request);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response response = call.execute();
//                    if (response.isSuccessful()) {
//                        InputStream is = response.body().byteStream();
//                        ByteArrayOutputStream os = new ByteArrayOutputStream();
//                        byte[] b = new byte[1024];
//                        int len = 0;
//                        while ((len = is.read(b)) != -1) {
//                            os.write(b, 0, len);
//                        }
//                        os.flush();
//                        final String s = new String(os.toByteArray());
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                listener.onsendJson(s);
////                                dialog.cancel();
////                                dialog.dismiss();
//                            }
//                        });
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }
}


