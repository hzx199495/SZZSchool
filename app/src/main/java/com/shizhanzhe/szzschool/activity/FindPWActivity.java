package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
 * Created by hasee on 2016/11/10.
 */
@ContentView(R.layout.dialog_find_pass)
public class FindPWActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.forget_tel)
    EditText tel;
    @ViewInject(R.id.forget_yzm)
    EditText writeYZM;
    @ViewInject(R.id.get_yzm)
    Button YZM;
    @ViewInject(R.id.forget_next)
    TextView next;
    @ViewInject(R.id.reset_new_pass)
    EditText ed1;
    @ViewInject(R.id.reset_new_pass_two)
    EditText ed2;

    private final Integer NUM = 6;
    private CountDownTimer time;
    String code = "";
    String username;
    String password;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        dialog = new Dialog(this, R.style.Dialog_Fullscreen);
        dialog.setContentView(R.layout.dialog_resetpass);
        time = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                YZM.setClickable(false);
                YZM.setText(millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                YZM.setText("重新验证");
                YZM.setClickable(true);
            }
        };
        YZM.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_yzm:
                //随机生成 num 位验证码
                code = "";
                Random r = new Random(new Date().getTime());
                for (int i = 0; i < NUM; i++) {
                    code = code + r.nextInt(10);
                }
                Log.i("=====", code);
                username = tel.getText().toString();

                if (username != null && username.length() == 11) {
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
                    Toast.makeText(getApplicationContext(), "已发送验证码，请注意接收", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "手机号长度有误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.forget_next:

                if (tel.length() == 11) {
                    if (writeYZM != null) {
                        forget();
                    } else {
                        Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "手机号长度有误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void forget() {
        if (code.equals(writeYZM.getText().toString())) {
            dialog.show();
            final EditText ed1 = (EditText) dialog.getWindow().findViewById(R.id.reset_new_pass);
            final EditText ed2 = (EditText) dialog.getWindow().findViewById(R.id.reset_new_pass_two);
            dialog.getWindow().findViewById(R.id.reset).
            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String p1 = ed1.getText().toString();
                    String p2 = ed2.getText().toString();
                    if (p1.length() != 0 && p2.length() != 0) {
                        if (p1.equals(p2)) {
                            OkHttpDownloadJsonUtil.downloadJson(FindPWActivity.this, Path.CHANGE(MyApplication.zh, p1,MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                                @Override
                                public void onsendJson(String json) {
                                    Gson gson = new Gson();
                                    RegisterBean bean = gson.fromJson(json, RegisterBean.class);
                                    if (bean.getStatus() == 1) {
                                        Toast.makeText(FindPWActivity.this, bean.getInfo(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else if (bean.getStatus() == 2) {
                                        Toast.makeText(FindPWActivity.this, bean.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(FindPWActivity.this, "密码输入不一致", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(FindPWActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    }


                }

            });
            }else{
                Toast.makeText(getApplicationContext(), "验证码不匹配,请重新获取", Toast.LENGTH_SHORT).show();
            }

        }
    }
