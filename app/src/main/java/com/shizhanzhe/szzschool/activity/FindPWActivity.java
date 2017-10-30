package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * Created by hasee on 2016/11/10.
 * 忘记密码
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
    @ViewInject(R.id.ll)
    LinearLayout ll;
    @ViewInject(R.id.set)
    LinearLayout set;
    @ViewInject(R.id.reset_new_pass)
    EditText ed1;
    @ViewInject(R.id.reset_new_pass_two)
    EditText ed2;
    @ViewInject(R.id.reset)
    TextView reset;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.back2)
    ImageView back2;
    private final Integer NUM = 6;
    private CountDownTimer time;
    private String code = "";
    private String username="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
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
        back.setOnClickListener(this);
        back2.setOnClickListener(this);
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
                username = tel.getText().toString();

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
                    new SVProgressHUD(FindPWActivity.this).showSuccessWithStatus("已发送验证码，请注意接收");
                } else {
                    new SVProgressHUD(FindPWActivity.this).showErrorWithStatus("请输入正确手机号");
                }
                break;
            case R.id.forget_next:

                if (isMobileNO(tel.getText().toString())) {
                    if (writeYZM.getText().toString().length()>=6) {

                        forget();
                    } else {
                        new SVProgressHUD(FindPWActivity.this).showInfoWithStatus("请输入验证码");
                    }
                } else {
                    new SVProgressHUD(FindPWActivity.this).showErrorWithStatus("请输入正确手机号");
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.back2:
                finish();
                break;
        }
    }

    public void forget() {
        if (code.equals(writeYZM.getText().toString())) {
            ll.setVisibility(View.GONE);
            set.setVisibility(View.VISIBLE);
            reset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String p1 = ed1.getText().toString();
                            String p2 = ed2.getText().toString();
                            if (p1.length() != 0 && p2.length() != 0) {
                                if (p1.equals(p2)) {
                                    OkHttpDownloadJsonUtil.downloadJson(FindPWActivity.this, new Path(FindPWActivity.this).FORGET(username,p1), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                                        @Override
                                        public void onsendJson(String json) {
                                            Gson gson = new Gson();
                                            RegisterBean bean = gson.fromJson(json, RegisterBean.class);
                                            if (bean.getStatus() == 1) {
                                                new SVProgressHUD(FindPWActivity.this).showSuccessWithStatus(bean.getInfo());
                                            } else if (bean.getStatus() == 2) {
                                                new SVProgressHUD(FindPWActivity.this).showInfoWithStatus(bean.getInfo());
                                            }
                                        }
                                    });
                                } else {
                                    new SVProgressHUD(FindPWActivity.this).showErrorWithStatus("密码输入不一致");
                                }
                            } else {
                                new SVProgressHUD(FindPWActivity.this).showErrorWithStatus("密码不能为空");
                            }


                        }

                    });
        } else {
            new SVProgressHUD(FindPWActivity.this).showErrorWithStatus("验证码不匹配");
        }

    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
