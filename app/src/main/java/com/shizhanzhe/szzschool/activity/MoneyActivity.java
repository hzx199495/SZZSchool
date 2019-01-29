package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.PayBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.MoneyAdapter;
import com.shizhanzhe.szzschool.pay.Pay;
import com.shizhanzhe.szzschool.utils.ItemModel;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.StatusBarUtil;
import com.shizhanzhe.szzschool.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hasee on 2016/11/28.
 * 充值
 */
@ContentView(R.layout.dialog_cz)
public class MoneyActivity extends Activity {
    @ViewInject(R.id.tv_recharge_money)
    TextView tv_recharge_money;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.btn_czpay)
    Button btnpay;
    @ViewInject(R.id.cz_zh)
    TextView cz_zh;
    @ViewInject(R.id.recylerview)
    RecyclerView recyclerView;
    @ViewInject(R.id.zfb)
    RadioButton zfb;
    @ViewInject(R.id.wx)
    RadioButton wx;
    @ViewInject(R.id.iv1)
    ImageView iv1;
    @ViewInject(R.id.iv2)
    ImageView iv2;
    private IWXAPI mWXApi;
    private PayBean payBean;
    private MoneyAdapter adapter;
    private QMUITipDialog mdialog;
    private   Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mdialog.dismiss();
                    break;
                case 2:
                    WXPay(payBean.getOrder(),payBean.getPrice());
                    break;
            }
        }
    };
     private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setStatusBarColor(this,R.color.white); }
        preferences= getSharedPreferences("userjson", Context.MODE_PRIVATE);
        String mobile = preferences.getString("mobile", "");
        cz_zh.setText(mobile);
        recyclerView.setHasFixedSize(true);
        //recycleview设置布局方式，GridView (一行三列)
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter = new MoneyAdapter());
        adapter.replaceAll(getData(), this);
        EventBus.getDefault().register(this);
        zfb.setChecked(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = tv_recharge_money.getText().toString();
                str = str.replace("元", "");
                if (!"".equals(str)) {
                    if (zfb.isChecked()) {
                        new Pay(MoneyActivity.this, str, "账户充值" + str + "元", new Pay.PayListener() {
                            @Override
                            public void refreshPriorityUI(int type) {
                             if (type==1){
                                 mdialog = new QMUITipDialog.Builder(MoneyActivity.this).setIconType(2).setTipWord("支付成功").create();
                                 mdialog.show();
                                 mhandler.sendEmptyMessageDelayed(1,1500);
                             }  else if (type==2){
                                 mdialog = new QMUITipDialog.Builder(MoneyActivity.this).setIconType(3).setTipWord("支付失败").create();
                                 mdialog.show();
                                 mhandler.sendEmptyMessageDelayed(1,1500);
                             }else if (type==3){
                                 mdialog = new QMUITipDialog.Builder(MoneyActivity.this).setIconType(4).setTipWord("支付取消").create();
                                 mdialog.show();
                                 mhandler.sendEmptyMessageDelayed(1,1500);
                             }
                            }
                        });
                    } else if(wx.isChecked()) {
                        Toast.makeText(MoneyActivity.this, "正在调起支付...", Toast.LENGTH_SHORT).show();
                        mWXApi = WXAPIFactory.createWXAPI(MoneyActivity.this, null);
                        // 将该app注册到微信
                        mWXApi.registerApp("wxa8e3fcf40642c20b");
                        if (!check()){
                            Toast.makeText(MoneyActivity.this, "未安装微信或微信版本过低", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String uid = preferences.getString("uid", "");
                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = new FormBody.Builder()
                                .add("uid", uid).add("price", str)
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
                                String a[] = s.split("</script>");
                                Gson gson = new Gson();
                                payBean = gson.fromJson(a[1], PayBean.class);
                                mhandler.sendEmptyMessage(2);
                                Log.i("充值订单号", a[1]+"订单号" + payBean.getInfo()+payBean.getOrder());

                            }
                        });

                    }
                } else {
                    mdialog = new QMUITipDialog.Builder(MoneyActivity.this).setIconType(4).setTipWord("金额不能为空！").create();
                    mdialog.show();
                    mhandler.sendEmptyMessageDelayed(1,1500);
                }
            }
        });
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zfb.setChecked(true);
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wx.setChecked(true);
            }
        });
    }

    public ArrayList<ItemModel> getData() {
        String data = "5,50,100,500,1000";
        String dataArr[] = data.split(",");
        ArrayList<ItemModel> list = new ArrayList<>();
        for (int i = 0; i < dataArr.length; i++) {
            String count = dataArr[i] + "元";
            list.add(new ItemModel(ItemModel.TWO, count));
        }
        list.add(new ItemModel(ItemModel.THREE, null));

        return list;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAdapterClickInfo(ItemModel model) {
        String money = model.data.toString().replace("元", "");
        tv_recharge_money.setText(money + "元");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void WXPay(long order,double price){
        String path=Path.WXPay(order,price);
        OkHttpDownloadJsonUtil.downloadJson(MoneyActivity.this, path, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                try {
                    JSONObject object = new JSONObject(json);
                    PayReq request = new PayReq();
                    request.appId = "wxa8e3fcf40642c20b";
                    request.partnerId = "1509113501";
                    request.prepayId= object.optString("prepayid");
                    request.packageValue = "Sign=WXPay";
                    request.nonceStr=object.optString("noncestr");
                    request.timeStamp=object.optString("timestamp");
                    request.sign=object.optString("sign");
                    mWXApi.sendReq(request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    //检测是否支持微信支付
    private boolean check() {
        return mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }

}
