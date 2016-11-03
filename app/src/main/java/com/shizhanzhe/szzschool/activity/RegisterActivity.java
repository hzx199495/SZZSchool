package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shizhanzhe.szzschool.R;


/**
 * Created by hasee on 2016/6/22.
 */
public class RegisterActivity extends Activity {
    private CountDownTimer time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                time.start();
                Toast.makeText(getApplicationContext(),"已发送验证码，请注意接收",Toast.LENGTH_SHORT).show();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
