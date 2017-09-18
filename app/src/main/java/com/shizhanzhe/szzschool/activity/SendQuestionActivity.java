package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.PolyvPlayerActivity;
import com.shizhanzhe.szzschool.video.PolyvPlayerEndFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by zz9527 on 2017/8/7.
 * 提问
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
                if (getIntent().getStringExtra("type").equals("1")){
                    if (!"".equals(et_talk.getText().toString())) {
                        OkHttpDownloadJsonUtil.downloadJson(SendQuestionActivity.this, new Path(SendQuestionActivity.this).SENDQUESTION("", MyApplication.txId , MyApplication.videotypeid, MyApplication.videoitemid, et_talk.getText().toString()), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                            @Override
                            public void onsendJson(String json) {
                                if (json.contains("1")) {
                                    Toast.makeText(SendQuestionActivity.this, "提问成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SendQuestionActivity.this, "提问失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        finish();
                    } else {
                        Toast.makeText(SendQuestionActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    }
                }else if (getIntent().getStringExtra("type").equals("回复")){
                    if (!"".equals(et_talk.getText().toString())) {

                        OkHttpDownloadJsonUtil.downloadJson(SendQuestionActivity.this, new Path(SendQuestionActivity.this).ANSWERQUESTION(getIntent().getStringExtra("coid"),getIntent().getStringExtra("uid") ,et_talk.getText().toString(), getIntent().getStringExtra("qid")), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                            @Override
                            public void onsendJson(String json) {
                                if (json.contains("1")) {
                                    Toast.makeText(SendQuestionActivity.this, "回复成功！", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(SendQuestionActivity.this, "回复失败，请重试！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(SendQuestionActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (!"".equals(et_talk.getText().toString())) {
                        Intent intent = new Intent(SendQuestionActivity.this, QuestionListActivity.class);
                        intent.putExtra("msg", et_talk.getText().toString());
                        setResult(11, intent);
                        finish();
                    } else {
                        Toast.makeText(SendQuestionActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
