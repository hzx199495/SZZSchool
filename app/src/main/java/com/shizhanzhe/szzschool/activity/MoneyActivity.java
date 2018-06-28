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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.MoneyAdapter;
import com.shizhanzhe.szzschool.pay.Pay;
import com.shizhanzhe.szzschool.utils.ItemModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

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
    private MoneyAdapter adapter;
    private QMUITipDialog mdialog;
    private   Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mdialog.dismiss();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
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
                            public void refreshPriorityUI() {
                                Intent intent = new Intent(MoneyActivity.this, UserZHActivity.class);
                                setResult(2, intent);
                                finish();
                            }
                        });
                    } else if(wx.isChecked()) {
                        Toast.makeText(MoneyActivity.this, "微信支付暂未开放", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mdialog = new QMUITipDialog.Builder(MoneyActivity.this).setIconType(3).setTipWord("金额不能为空！").create();
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
}
