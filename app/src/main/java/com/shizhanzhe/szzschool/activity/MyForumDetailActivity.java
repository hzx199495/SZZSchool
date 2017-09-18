package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.BKBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.ForumBKLVAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zz9527 on 2017/9/15.
 */
@ContentView(R.layout.activity_myforumdeatil)
public class MyForumDetailActivity extends Activity{
    @ViewInject(R.id.lv)
    ListView lv;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.title)
    TextView title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        final String fid = getIntent().getStringExtra("fid");
        title.setText(getIntent().getStringExtra("title"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        OkHttpDownloadJsonUtil.downloadJson(this, new Path(this).MYFORUM(fid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                try {
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd")
                            .create();
                    final List<BKBean> list = gson.fromJson(json, new TypeToken<List<BKBean>>() {
                    }.getType());
                    if (list.size() > 0) {
                        ForumBKLVAdapter adapter = new ForumBKLVAdapter(MyForumDetailActivity.this, list);
                        lv.setAdapter(adapter);
                    } else {
                        lv.setVisibility(View.GONE);
                    }
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String title = list.get(position).getSubject();
                                    String name = list.get(position).getRealname();
                                    String time = list.get(position).getDateline();
                                    String pid = list.get(position).getPid();
                                    String logo = list.get(position).getLogo();
                                    String rep = list.get(position).getAlltip();
                                    String authorid = list.get(position).getAuthorid();

                                    Intent intent = new Intent(getApplicationContext(), ForumItemActivity.class);
                                    intent.putExtra("pid", pid);
                                    intent.putExtra("title", title);
                                    intent.putExtra("name", name);
                                    intent.putExtra("img", logo);
                                    intent.putExtra("time", time);
                                    intent.putExtra("rep", rep);
                                    intent.putExtra("fid", fid);
                                    intent.putExtra("authorid", authorid);
                                    startActivity(intent);
                        }
                    });
                }catch (Exception e){}

            }
        });

    }
}
