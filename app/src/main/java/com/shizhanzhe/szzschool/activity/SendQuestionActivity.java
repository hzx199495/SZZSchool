package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shizhanzhe.szzschool.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by zz9527 on 2017/8/7.
 */
@ContentView(R.layout.polyv_activity_talk_edittext)
public class SendQuestionActivity extends Activity {
    @ViewInject(R.id.et_talk)
    EditText et_talk;
    @ViewInject(R.id.tv_send)
    TextView tv_send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"".equals(et_talk.getText().toString())) {
                    Intent intent = new Intent(SendQuestionActivity.this, QuestionListActivity.class);
                    intent.putExtra("msg",et_talk.getText().toString());
                    setResult(11, intent);
                    finish();
                } else {
                    Toast.makeText(SendQuestionActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
