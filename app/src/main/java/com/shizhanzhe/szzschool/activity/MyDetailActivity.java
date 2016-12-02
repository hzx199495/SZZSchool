package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easefun.polyvsdk.demo.IjkVideoActicity;
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
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<String> url = new ArrayList<String>();
        name = getIntent().getStringArrayListExtra("name");
        url = getIntent().getStringArrayListExtra("url");
        String title = getIntent().getStringExtra("title");
        tv.setText(title);
        ListAdapter adapter = new ListAdapter(MyDetailActivity.this, name,url);
        lv.setAdapter(adapter);
        final ArrayList<String> finalUrl = url;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IjkVideoActicity.intentTo(MyDetailActivity.this, 4, 1, finalUrl.get(position),
                        false);
//                Toast.makeText(getApplicationContext(),finalUrl.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }


}

