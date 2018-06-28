package com.shizhanzhe.szzschool.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.PayBean;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 重要说明:
 * <p>
 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
 */
public class Pay {
    Context context;
    Activity activity;
    String price;
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016111602874326";


    /**
     * 商户私钥，pkcs8格式
     */
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM86f2dVKswmSDMg\n" +
            "HmJiHIhiXPB5Tb1pu3im8q4E5ZGC9/52rotlEqatu5css//SwY/qktTYi9wdXANK\n" +
            "K4jzQqDlgr226Czndv82JaGjepqJrB+49Z3EBustlK9B2Kq1RPy2kRY4HeFLtwEa\n" +
            "LouWZs657HxV4dgBxwEyzJwTYFO7AgMBAAECgYAaQd9CyJfTpkYftEIlVSkoXs5H\n" +
            "6hbfmTYX4498XUJ5XY7uvzr+jp/+XqUWUqO8FohQI/jW31lZWp7+C7fYcnpOjiQP\n" +
            "1ixpkjnj7/X+dgQ+6jZ2a6PV7I9CPHa/0LZSq6QOSECt44YXO5ffFsCSHr52ehVN\n" +
            "2zM2HMGvQpv+TpaCgQJBAP1Q+C3yIjknjVZkHu9nGo3e4H7rxacUuy7DKPoOZX8X\n" +
            "2it6dRhlUqW/D+Mmeolt8GuKx1h/wwN3pKlGXJKzfE8CQQDRbIgOQYSOop14W/LL\n" +
            "q0VVaJpqD7cNfs152wLYH/4fR3cBAjdv/79tw9IuqBnzTgpvj1XGmUlHkJgWvfUw\n" +
            "IDrVAkEApiO/0iqXpZK6USnzXCLsOJYv+S5bgPzI05+uNWrRyKx1K8TyHaD+trR8\n" +
            "EAgBoDcZ0v6/Rdzto02KEneSaakMYwJADEIKBKuZErA5/12CbKFtwX4J68NXt5aA\n" +
            "eG11USkuOq54LLrwe8HAdrBLkSeNvUwIGFePcbxvKZ0xCcO8okCAsQJBAMIdKxxw\n" +
            "WCguZkKt9fMG8PhfEeBS/UB/2GJKsKImkxFKkQuGwrlFwAaR/wTUDI7bXr3BzYMk\n" +
            "JbvM28sgF6jCqYA=";

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        listener.refreshPriorityUI();
                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    PayListener listener;

    public interface PayListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
         void refreshPriorityUI();
    }

    public Pay(Activity activity, final String price, final String subject, PayListener listener) {
        this.listener = listener;
        this.price = price;
        this.activity = activity;
        this.context = activity.getApplicationContext();
        SharedPreferences preferences = context.getSharedPreferences("userjson", Context.MODE_PRIVATE);
        String uid = preferences.getString("uid", "");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("uid", uid).add("price", price)
                .build();
        //在构建Request对象时，调用post方法，传入RequestBody对象
        Request request = new Request.Builder()
                .url("https://shizhanzhe.com/index.php?m=member.save_bone&pc=1")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("fail", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Log.i("success", s);
                String a[] = s.split("</script>");
                Gson gson = new Gson();
                PayBean payBean = gson.fromJson(a[1], PayBean.class);
                long order = payBean.getOrder();
                Log.i("充值订单号", a[1]+"订单号" + payBean.getInfo()+payBean.getOrder());
                payV2(order, price, subject);
            }
        });

    }

    public Pay(Activity activity, final String price, final String subject, String path, PayListener listener) {
        this.listener = listener;
        this.price = price;
        this.activity = activity;
        this.context = activity.getApplicationContext();
        OkHttpDownloadJsonUtil.downloadJson(context, path, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {

                Gson gson = new Gson();
                PayBean bean = gson.fromJson(json, PayBean.class);
                long order = bean.getOrder();
                Log.i("打赏订单号", "订单号" + json + "——" + order);
                payV2(order, price, subject);
            }
        });
    }

    /**
     * 支付宝支付业务
     *
     * @param
     */
    public void payV2(long order, String price, String subject) {
        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)) {
            new AlertDialog.Builder(context).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //

                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         *
         */

        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, order, price, subject);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        Log.i("拼接字符串", "拼接字符串" + orderParam);
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
        final String orderInfo = orderParam + "&" + sign;
        Log.i("orderInfo=", orderParam);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


}
