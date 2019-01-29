package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.RegisterBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.AlidayuMessage;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.StatusBarUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private QMUITipDialog dialog;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    dialog.dismiss();
                    break;
            }
        }
    };
    private final Integer NUM=4;
    private CountDownTimer time;
    private String code="";
    private String username;
    private  String password;
    private long timestamp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setStatusBarColor(this,R.color.white); }
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
                    Toast.makeText(RegisterActivity.this, "正在请求...", Toast.LENGTH_SHORT).show();
//                    AlidayuMessage.setRecNum(username);
//                    AlidayuMessage.setSmsParam(code);
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                AlidayuMessage.SendMsg();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }).start();
                    try {
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                        String nowdate = sd.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
                        Date date = sd.parse(nowdate);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        timestamp = cal.getTimeInMillis(); //单位为毫秒
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    final String yuliu = new StringBuffer(Integer.parseInt(code)*51-1314+username.substring(7)+username.substring(3,7)+timestamp/1000+"ba").reverse().toString();
                    OkHttpDownloadJsonUtil.downloadJson(RegisterActivity.this, Path.REGISTERCODE(code,yuliu,username), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                        @Override
                        public void onsendJson(String data) {
                            String json = data.substring(data.length()-1);
                            if (json.contains("0")){
                                dialog = new QMUITipDialog.Builder(RegisterActivity.this).setIconType(4).setTipWord("非法操作").create();
                            }else if (json.contains("1")){
                                time.start();
                                dialog = new QMUITipDialog.Builder(RegisterActivity.this).setIconType(2).setTipWord("已发送验证码，请注意接收").create();
                            }else if (json.contains("2")){
                                dialog = new QMUITipDialog.Builder(RegisterActivity.this).setIconType(4).setTipWord("短信服务故障或发送已达上限，请明日再试").create();
                            }else if (json.contains("3")){
                                dialog = new QMUITipDialog.Builder(RegisterActivity.this).setIconType(4).setTipWord("该手机号已经注册").create();
                            }
                            dialog.show();
                            mhandler.sendEmptyMessageDelayed(1,1500);
                        }
                    });

//                    time.start();
//                    dialog = new QMUITipDialog.Builder(RegisterActivity.this).setIconType(4).setTipWord("已发送验证码，请注意接收").create();
//                    dialog.show();
//                    mhandler.sendEmptyMessageDelayed(1,1500);
                }else{
                    dialog = new QMUITipDialog.Builder(RegisterActivity.this).setIconType(4).setTipWord("请输入正确手机号").create();
                    dialog.show();
                    mhandler.sendEmptyMessageDelayed(1,1500);
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
                                dialog = new QMUITipDialog.Builder(RegisterActivity.this).setIconType(4).setTipWord(bean.getInfo()).create();
                                dialog.show();
                                mhandler.sendEmptyMessageDelayed(1,1500);
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                dialog = new QMUITipDialog.Builder(RegisterActivity.this).setIconType(4).setTipWord(bean.getInfo()).create();
                                dialog.show();
                                mhandler.sendEmptyMessageDelayed(1,1500);
                            }
                        }
                    });

                }else{
                    dialog = new QMUITipDialog.Builder(RegisterActivity.this).setIconType(4).setTipWord("验证码不匹配").create();
                    dialog.show();
                    mhandler.sendEmptyMessageDelayed(1,1500);
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
        Pattern p = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|16[6]|19[89]]|17[01345678]|18[0-9]|19[0-9]|14[579])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
