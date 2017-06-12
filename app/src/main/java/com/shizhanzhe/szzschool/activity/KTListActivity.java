package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.KTListBean;
import com.shizhanzhe.szzschool.Bean.MyKTBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.KTAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zz9527 on 2017/3/14.
 */
@ContentView(R.layout.activity_ktlist)
public class KTListActivity extends Activity{
    @ViewInject(R.id.list)
    ListView lv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        Intent intent = getIntent();
        String tuanid = intent.getStringExtra("tuanid");
        final String tgtitle=intent.getStringExtra("title");
        OkHttpDownloadJsonUtil.downloadJson(this, "http://shizhanzhe.com/index.php?m=pcdata.kaituancha&pc=1&tid="+tuanid, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                List<KTListBean> list = gson.fromJson(json, new TypeToken<List<KTListBean>>() {
                }.getType());
                lv.setAdapter(new KTAdapter(getApplicationContext(),list,tgtitle));
            }
        });
    }
}
