package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
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

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by zz9527 on 2017/9/4.
 * 视频笔记
 */
@ContentView(R.layout.polyv_activity_talk_edittext)
public class VideoNoteActivity extends Activity {
    @ViewInject(R.id.et_talk)
    EditText et_talk;
    @ViewInject(R.id.tv_send)
    TextView tv_send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        et_talk.setHint("请输入笔记内容");
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!"".equals(et_talk.getText().toString())) {
                    OkHttpDownloadJsonUtil.downloadJson(VideoNoteActivity.this, new Path(VideoNoteActivity.this).NOTELISTEADD(MyApplication.txId, MyApplication.videotypeid, MyApplication.videoitemid,et_talk.getText().toString()), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                        @Override
                        public void onsendJson(String json) {
                            if (json.contains("1")){
                                Toast.makeText(VideoNoteActivity.this,"记录成功",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(VideoNoteActivity.this,"记录失败",Toast.LENGTH_LONG).show();
                            }
                            finish();
                        }
                    });
                } else {
                    Toast.makeText(VideoNoteActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
