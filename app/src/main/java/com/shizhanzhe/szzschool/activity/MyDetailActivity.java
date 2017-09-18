package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.ListAdapter;
import com.shizhanzhe.szzschool.utils.Path;
//import com.shizhanzhe.szzschool.video.IjkVideoActicity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hasee on 2016/11/29.
 *
 */
@ContentView(R.layout.activity_mydetail)
public class MyDetailActivity extends Activity {
    @ViewInject(R.id.mydetail_lv)
    ListView lv;
    @ViewInject(R.id.proxj)
    TextView tv;
    @ViewInject(R.id.back)
    ImageView back;
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
        String img=getIntent().getStringExtra("img");
        String title = getIntent().getStringExtra("title");
        final String sid = getIntent().getStringExtra("sid");
        final String spid = getIntent().getStringExtra("spid");
        tv.setText(title);
        ListAdapter adapter = new ListAdapter(MyDetailActivity.this, proname);
        lv.setAdapter(adapter);
        final ArrayList<String> finalPid = pid;
        final ArrayList<String> finalProname = proname;
        final ArrayList<String> finalUrl = url;
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String comment= Path.COMMENT(finalPid.get(position), MyApplication.myid, MyApplication.token);
//                IjkVideoActicity.intentTo(MyDetailActivity.this, IjkVideoActicity.PlayMode.portrait, IjkVideoActicity.PlayType.vid, finalUrl.get(position),
//                        false,comment,sid,spid, finalPid.get(position), finalProname.get(position));
//            }
//        });
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

    }


}

