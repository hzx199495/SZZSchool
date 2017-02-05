package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.RegisterBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.AlidayuMessage;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Date;
import java.util.Random;


/**
 * Created by hasee on 2016/6/22.
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends Activity {
    @ViewInject(R.id.MobileNum)
    EditText mobileNum;
    @ViewInject(R.id.txtAuthCode)
    EditText dayu;
    @ViewInject(R.id.edit_qrpsw)
    EditText qrpsw;
    private final Integer NUM=6;
    private CountDownTimer time;
    String code;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        final Button btnSendAuth = (Button) findViewById(R.id.btnSendAuth);
        time = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnSendAuth.setClickable(false);
                btnSendAuth.setText(millisUntilFinished /1000+"秒");
            }

            @Override
            public void onFinish() {
                btnSendAuth.setText("重新验证");
                btnSendAuth.setClickable(true);
            }
        };
        Button btnFinish = (Button) findViewById(R.id.btnFinish);
        btnSendAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //随机生成 num 位验证码
                Random r = new Random(new Date().getTime());
                for(int i=0;i<NUM;i++){
                    code = code+r.nextInt(10);
                }
                 username = mobileNum.getText().toString();

                if (username != null&&username.length()==11) {
                    AlidayuMessage.setRecNum(username);
                    AlidayuMessage.setSmsParam(code);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                AlidayuMessage.SendMsg();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    time.start();
                    Toast.makeText(getApplicationContext(),"已发送验证码，请注意接收",Toast.LENGTH_SHORT).show();
                }else{
                 Toast.makeText(getApplicationContext(),"手机号长度有误",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = qrpsw.getText().toString();
                if(code.equals(dayu.getText().toString())){
                    OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), Path.REGISTER(username, password), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                        @Override
                        public void onsendJson(String json) {
                            Log.i("====",username+"__"+password);
                            Gson gson = new Gson();
                            RegisterBean bean = gson.fromJson(json, RegisterBean.class);
                            if (bean.getStatus()==1){
                                Toast.makeText(RegisterActivity.this, bean.getInfo(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else if(bean.getStatus()==2){
                                Toast.makeText(RegisterActivity.this,bean.getInfo(),Toast.LENGTH_SHORT).show();
                            }else if (bean.getStatus()==3){
                                Toast.makeText(RegisterActivity.this,bean.getInfo(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                        Toast.makeText(getApplicationContext(),"验证码不匹配,请重新获取",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
