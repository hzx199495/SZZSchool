package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.LoginBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.pay.Pay;
import com.shizhanzhe.szzschool.utils.MyDialog;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;

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
    @ViewInject(R.id.xf)
    TextView xf;
    @ViewInject(R.id.back)
    ImageView back;
    Dialog dialog;
    EditText czmoney;
    TextView zh;
    Button btnpay;
    String mymoney;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);

        money.setText(MyApplication.money);
//         dialog = new Dialog(this,R.style.Dialog_Fullscreen);
//        dialog.setContentView(R.layout.dialog_cz);
        cz.setOnClickListener(this);
        xf.setOnClickListener(this);
        back.setOnClickListener(this);
            }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recharge:
                dialog = new Dialog(this, R.style.popupDialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_cz);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(true);
                WindowManager.LayoutParams lay = dialog.getWindow().getAttributes();
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                Rect rect = new Rect();
                View view = getWindow().getDecorView();//decorView是window中的最顶层view，可以从window中获取到decorView
                view.getWindowVisibleDisplayFrame(rect);
                lay.height = dm.heightPixels - rect.top;
                lay.width = dm.widthPixels;
                dialog.show();
                czmoney = (EditText) dialog.getWindow().findViewById(R.id.edit_money);
                zh = (TextView) dialog.getWindow().findViewById(R.id.cz_zh);
                btnpay = (Button) dialog.getWindow().findViewById(R.id.btn_czpay);
                btnpay.setOnClickListener(this);
                zh.setText("充值账户:"+MyApplication.zh);
                break;
            case R.id.btn_czpay:
                String str = czmoney.getText().toString();
                if(str!=null&!"".equals(str)){
                    new Pay(UserZHActivity.this, str, new Pay.PayListener() {
                        @Override
                        public void refreshPriorityUI(String string) {
                            money.setText(string);
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"金额不能为空",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.xf:
                startActivity(new Intent(UserZHActivity.this,XFActivity.class));
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

}

