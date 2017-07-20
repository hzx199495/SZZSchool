package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.pay.Pay;
import com.shizhanzhe.szzschool.video.PolyvTalkEdittextActivity;
import com.shizhanzhe.szzschool.video.PolyvTalkFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by hasee on 2016/11/28.
 */
@ContentView(R.layout.dialog_cz)
public class MoneyActivity extends Activity {
    @ViewInject(R.id.edit_money)
    EditText czmoney;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.btn_czpay)
    Button btnpay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = czmoney.getText().toString();
                if(!"".equals(str)){
                    new Pay(MoneyActivity.this, str,"账户充值"+str,new Pay.PayListener() {
                        @Override
                        public void refreshPriorityUI() {
                            Intent intent = new Intent(MoneyActivity.this, UserZHActivity.class);
                            setResult(2, intent);
                            finish();
                        }
                    });
                }else{
                    new SVProgressHUD(MoneyActivity.this).showInfoWithStatus("请填写金额");
                }
            }
        });
    }
}
