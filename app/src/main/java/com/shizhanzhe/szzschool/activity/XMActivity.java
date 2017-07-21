package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.RegisterBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by zz9527 on 2017/7/20.
 * 修改密码
 */
@ContentView(R.layout.activity_xm)
public class XMActivity extends Activity{
    @ViewInject(R.id.reset_new_pass)
    EditText ed1;
    @ViewInject(R.id.reset_new_pass_two)
    EditText ed2;
    @ViewInject(R.id.reset)
    TextView reset;
    @ViewInject(R.id.back)
    ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p1 = ed1.getText().toString();
                String p2 = ed2.getText().toString();
                if (p1.length() != 0 && p2.length() != 0) {
                    if (p1.equals(p2)) {
                        OkHttpDownloadJsonUtil.downloadJson(XMActivity.this, new Path(XMActivity.this).CHANGE(p1), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                            @Override
                            public void onsendJson(String json) {
                                Gson gson = new Gson();
                                RegisterBean bean = gson.fromJson(json, RegisterBean.class);
                                if (bean.getStatus() == 1) {
                                    new SVProgressHUD(XMActivity.this).showSuccessWithStatus(bean.getInfo());
                                } else if (bean.getStatus() == 2) {
                                    new SVProgressHUD(XMActivity.this).showInfoWithStatus(bean.getInfo());
                                }
                            }
                        });
                    } else {
                        new SVProgressHUD(XMActivity.this).showErrorWithStatus("密码输入不一致");
                    }
                } else {
                    new SVProgressHUD(XMActivity.this).showErrorWithStatus("密码不能为空");
                }


            }

        });
    }
}