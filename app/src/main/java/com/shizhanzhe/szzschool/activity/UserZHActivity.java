package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.pay.Pay;
import com.shizhanzhe.szzschool.utils.MyDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import okhttp3.internal.framed.Variant;

/**
 * Created by hasee on 2016/11/4.
 */
@ContentView(R.layout.activity_userzh)
public class UserZHActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.mymoney)
    TextView money;
    @ViewInject(R.id.recharge)
    TextView cz;
    Dialog dialog;
    EditText czmoney;
    TextView zh;
    Button btnpay;

    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        money.setText(MyApplication.money);
         dialog = new Dialog(this,R.style.Dialog_Fullscreen);
        dialog.setContentView(R.layout.dialog_cz);
        cz.setOnClickListener(this);
            }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recharge:
                dialog.show();
                czmoney = (EditText) dialog.getWindow().findViewById(R.id.edit_money);
                zh = (TextView) dialog.getWindow().findViewById(R.id.cz_zh);
                btnpay = (Button) dialog.getWindow().findViewById(R.id.btn_czpay);
                btnpay.setOnClickListener(this);
                zh.setText("充值账户:"+MyApplication.zh);
                break;
            case R.id.btn_czpay:
                String str = czmoney.getText().toString();
                new Pay(UserZHActivity.this, str, new Pay.PayListener() {
                    @Override
                    public void refreshPriorityUI(String string) {
                        money.setText(string);
                    }
                });
                break;
            default:
                break;
        }
    }
}

