package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.shizhanzhe.szzschool.Bean.CollectListBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.CollectAdapter;
import com.shizhanzhe.szzschool.db.DatabaseOpenHelper;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.DbManager;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2016/11/28.
 */
@ContentView(R.layout.activity_sc)
public class CollectActivity extends Activity {
    @ViewInject(R.id.lv_collect)
    ListView lv;
    @ViewInject(R.id.back)
    ImageView back;
    DbManager manager = DatabaseOpenHelper.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

            OkHttpDownloadJsonUtil.downloadJson(CollectActivity.this, Path.COLLECTLIST(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {

                    JsonParser parser = new JsonParser();

                    JsonArray jsonArray = parser.parse(json).getAsJsonArray();

                    Gson gson = new Gson();
                    ArrayList<List<CollectListBean.SysinfoBean>> sysinfo = new ArrayList<>();
                    ArrayList<String> listId = new ArrayList<>();

                    //加强for循环遍历JsonArray
                    for (JsonElement pro : jsonArray) {
                        //使用GSON，直接转成Bean对象
                        CollectListBean Bean = gson.fromJson(pro, CollectListBean.class);
                        sysinfo.add(Bean.getSysinfo());
                        listId.add(Bean.getId());
                    }
                    CollectAdapter adapter = new CollectAdapter(getApplicationContext(), sysinfo, listId);
                    lv.setAdapter(adapter);
                }
            });
        }


}

