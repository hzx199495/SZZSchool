package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private final Integer NUM = 4;
    private CountDownTimer time;
    private String code = "";
    private String username="";
    private QMUITipDialog dialog;
    private long timestamp;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setStatusBarColor(this,R.color.white); }
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
                    Toast.makeText(FindPWActivity.this, "正在请求...", Toast.LENGTH_SHORT).show();
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

                    OkHttpDownloadJsonUtil.downloadJson(FindPWActivity.this, Path.FORGETCODE(code,yuliu,username), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

                        @Override
                        public void onsendJson(String data) {
                            String json = data.substring(data.length()-1);
                            if (json.contains("0")){
                                dialog = new QMUITipDialog.Builder(FindPWActivity.this).setIconType(4).setTipWord("非法操作").create();
                            }else if (json.contains("1")){
                                time.start();
                                dialog = new QMUITipDialog.Builder(FindPWActivity.this).setIconType(2).setTipWord("已发送验证码，请注意接收").create();
                            }else if (json.contains("2")){
                                dialog = new QMUITipDialog.Builder(FindPWActivity.this).setIconType(4).setTipWord("短信服务故障或发送已达上限，请明日再试").create();
                            }else if (json.contains("3")){
                                dialog = new QMUITipDialog.Builder(FindPWActivity.this).setIconType(4).setTipWord("用户不存在").create();
                            }
                            dialog.show();
                            mhandler.sendEmptyMessageDelayed(1,1500);
                        }
                    });
//                    time.start();
//                    dialog = new QMUITipDialog.Builder(this).setIconType(2).setTipWord("已发送验证码，请注意接收").create();
//                    dialog.show();
//                    mhandler.sendEmptyMessageDelayed(1,1500);
                } else {
                    dialog = new QMUITipDialog.Builder(this).setIconType(4).setTipWord("请输入正确手机号").create();
                    dialog.show();
                    mhandler.sendEmptyMessageDelayed(1,1500);
                }
                break;
            case R.id.forget_next:

                if (isMobileNO(tel.getText().toString())) {
                    if (writeYZM.getText().toString().length()>=6) {

                        forget();
                    } else {
                        dialog = new QMUITipDialog.Builder(this).setIconType(4).setTipWord("请输入验证码").create();
                        dialog.show();
                        mhandler.sendEmptyMessageDelayed(1,1500);
                    }
                } else {
                    dialog = new QMUITipDialog.Builder(this).setIconType(4).setTipWord("请输入正确手机号").create();
                    dialog.show();
                    mhandler.sendEmptyMessageDelayed(1,1500);
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
                                                dialog = new QMUITipDialog.Builder(FindPWActivity.this).setIconType(4).setTipWord(bean.getInfo()).create();
                                                dialog.show();
                                                mhandler.sendEmptyMessageDelayed(1,1500);
                                            } else if (bean.getStatus() == 2) {
                                                dialog = new QMUITipDialog.Builder(FindPWActivity.this).setIconType(4).setTipWord(bean.getInfo()).create();
                                                dialog.show();
                                                mhandler.sendEmptyMessageDelayed(1,1500);
                                            }
                                        }
                                    });
                                } else {
                                    dialog = new QMUITipDialog.Builder(FindPWActivity.this).setIconType(4).setTipWord("密码输入不一致").create();
                                    dialog.show();
                                    mhandler.sendEmptyMessageDelayed(1,1500);
                                }
                            } else {
                                dialog = new QMUITipDialog.Builder(FindPWActivity.this).setIconType(4).setTipWord("密码不能为空").create();
                                dialog.show();
                                mhandler.sendEmptyMessageDelayed(1,1500);

                            }


                        }

                    });
        } else {
            dialog = new QMUITipDialog.Builder(FindPWActivity.this).setIconType(4).setTipWord("验证码不匹配").create();
            dialog.show();
            mhandler.sendEmptyMessageDelayed(1,1500);
        }

    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|16[6]|19[89]]|17[01345678]|18[0-9]|19[0-9]|14[579])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static String md5(String input) throws NoSuchAlgorithmException {
        String result = input;
        if(input != null) {
            MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"
            md.update(input.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            result = hash.toString(16);
            while(result.length() < 32) { //31位string
                result = "0" + result;
            }
        }
        return result;
    }
}
