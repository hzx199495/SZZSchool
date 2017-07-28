package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by zz9527 on 2017/7/27.
 */
@ContentView(R.layout.activity_note)
public class NoteActivity extends Activity{
    @ViewInject(R.id.iv_finish)
    ImageView back;
    @ViewInject(R.id.tv_send)
    TextView tv_send;
    @ViewInject(R.id.et_msg)
    EditText et_msg;
    private String sid;
    private String pid;
    private String nid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initView();
    }
    private void initView() {
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_msg.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0 && et_msg.getText().toString().trim().length() > 0) {
                    tv_send.setEnabled(true);
                    tv_send.setTextColor(getResources().getColorStateList(R.color.polyv_send_text_color));
                } else {
                    tv_send.setEnabled(false);
                    tv_send.setTextColor(getResources().getColor(R.color.top_right_text_color_gray));
                }
            }
        });
        tv_send.setEnabled(false);
        tv_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OkHttpDownloadJsonUtil.downloadJson(NoteActivity.this, new Path(NoteActivity.this).NOTELISTEADD(MyApplication.txId, MyApplication.videotypeid, MyApplication.videoitemid,et_msg.getText().toString()), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        if (json.contains("1")){
                            Toast.makeText(NoteActivity.this,"记录成功",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
            }
        });
    }
}
