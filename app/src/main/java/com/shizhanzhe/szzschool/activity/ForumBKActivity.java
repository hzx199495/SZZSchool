package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.BKBean;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.ForumBKLVAdapter;
import com.shizhanzhe.szzschool.adapter.ForumLVAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by hasee on 2016/12/29.
 */
@ContentView(R.layout.activity_bk)
public class ForumBKActivity extends Activity {
    @ViewInject(R.id.bk_title)
    TextView title;
    @ViewInject(R.id.bk_lv)
    ListView lv;
    @ViewInject(R.id.puttext)
    TextView puttext;
    @ViewInject(R.id.back)
    ImageView back;
    List<BKBean> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        final Intent intent = getIntent();
        final String fid = intent.getStringExtra("fid");
        String name = intent.getStringExtra("name");
        title.setText(name);
        OkHttpDownloadJsonUtil.downloadJson(this, Path.FORUMBK(fid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Log.i("json",json);
                Gson gson = new Gson();
                list  = gson.fromJson(json, new TypeToken<List<BKBean>>() {
                }.getType());
                ForumBKLVAdapter adapter = new ForumBKLVAdapter(ForumBKActivity.this, list);
                lv.setAdapter(adapter);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = list.get(position).getSubject();
                String name = list.get(position).getRealname();
                long time = list.get(position).getDateline();
                String pid = list.get(position).getPid();
                String logo = list.get(position).getLogo();
                String rep = list.get(position).getAlltip();
                OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), "http://shizhanzhe.com/index.php?m=pcdata.add_num&pc=1&uid=" + MyApplication.myid + "&pid=" + pid + "+&token=" + MyApplication.token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {

                    }
                });
                Intent intent = new Intent(ForumBKActivity.this, ForumItemActivity.class);
                intent.putExtra("pid",pid);
                intent.putExtra("title",title);
                intent.putExtra("name",name);
                intent.putExtra("img",logo);
                intent.putExtra("time",time);
                intent.putExtra("rep",rep);
                intent.putExtra("fid",fid);
                startActivity(intent);
            }
        });
        puttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i= new Intent();
                i.setClass(ForumBKActivity.this,PostActivity.class);
                i.putExtra("fid",fid);
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
