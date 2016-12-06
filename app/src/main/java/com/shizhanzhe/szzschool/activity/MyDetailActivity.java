package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.ListAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hasee on 2016/11/29.
 */
@ContentView(R.layout.activity_mydetail)
public class MyDetailActivity extends Activity {
    @ViewInject(R.id.mydetail_lv)
    ListView lv;
    @ViewInject(R.id.proxj)
    TextView tv;
    ArrayList<String> parent;
    HashMap<String, List<String>> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        ArrayList<String> proname = new ArrayList<String>();
        ArrayList<String> url = new ArrayList<String>();
        ArrayList<String> pid = new ArrayList<String>();
        proname = getIntent().getStringArrayListExtra("name");
        url = getIntent().getStringArrayListExtra("url");
        pid = getIntent().getStringArrayListExtra("pid");
        String title = getIntent().getStringExtra("title");
        final String uid = getIntent().getStringExtra("uid");
        final String token = getIntent().getStringExtra("token");
        String sid = getIntent().getStringExtra("sid");
        String spid = getIntent().getStringExtra("spid");
        tv.setText(title);
        ListAdapter adapter = new ListAdapter(MyDetailActivity.this, proname,url,pid,uid,token,sid,spid);
        lv.setAdapter(adapter);

    }


}

