package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.pay.Pay;
import com.shizhanzhe.szzschool.video.PolyvTalkFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by zz9527 on 2017/7/4.
 *打赏
 */
@ContentView(R.layout.activity_reward)
public class RewardActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.btn1)
    Button btn1;
    @ViewInject(R.id.btn2)
    Button btn2;
    @ViewInject(R.id.btn3)
    Button btn3;
    @ViewInject(R.id.edit)
    EditText edit;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.yes)
    ImageView yes;
    private String tid;
    private String tuid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        this.setFinishOnTouchOutside(true);
        tid = getIntent().getStringExtra("fid");
        tuid = getIntent().getStringExtra("authorid");
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        yes.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                edit.setText("6.66");
                break;
            case R.id.btn2:
                edit.setText("8.88");
                break;
            case R.id.btn3:
                edit.setText("88.88");
                break;
            case R.id.back:
                finish();
                break;
            case R.id.yes:
                String str = edit.getText().toString();
                if(!"".equals(str)&&Double.parseDouble(str)>=1){
                    new Pay(RewardActivity.this, str,"打赏"+str+"元","https://shizhanzhe.com/index.php?m=courSystem.zanbuy&tid="+tid+"&money="+str+"&fromuid="+tuid, new Pay.PayListener(){
                        @Override
                        public void refreshPriorityUI() {

                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"金额不小于1",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
