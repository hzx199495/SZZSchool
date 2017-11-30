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
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by hasee on 2016/6/22.
 * 注册
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends Activity {
    @ViewInject(R.id.MobileNum)
    EditText mobileNum;
    @ViewInject(R.id.txtAuthCode)
    EditText dayu;
    @ViewInject(R.id.edit_qrpsw)
    EditText qrpsw;
    @ViewInject(R.id.zhlogin)
    TextView login;
    @ViewInject(R.id.tv)
    TextView tv;

    private final Integer NUM=6;
    private CountDownTimer time;
    private String code="";
    private String username;
    private  String password;
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
                code="";
                Random r = new Random(new Date().getTime());
                for(int i=0;i<NUM;i++){
                    code = code+r.nextInt(10);
                }
                 username = mobileNum.getText().toString();

                if (isMobileNO(username)) {
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
                    new SVProgressHUD(RegisterActivity.this).showSuccessWithStatus("已发送验证码，请注意接收");
                }else{
                    new SVProgressHUD(RegisterActivity.this).showErrorWithStatus("请输入正确手机号");
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
                            Gson gson = new Gson();
                            RegisterBean bean = gson.fromJson(json, RegisterBean.class);
                            if (bean.getStatus()==1){
                                new SVProgressHUD(RegisterActivity.this).showSuccessWithStatus(bean.getInfo());
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                new SVProgressHUD(RegisterActivity.this).showInfoWithStatus(bean.getInfo());
                            }
                        }
                    });

                }else{
                    new SVProgressHUD(RegisterActivity.this).showErrorWithStatus("验证码不匹配");
                }

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,TermsActivity.class));
            }
        });
    }
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
