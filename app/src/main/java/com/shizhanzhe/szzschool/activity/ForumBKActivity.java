package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    String qx="";
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("未购买该体系,无法查看");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        final Intent intent = getIntent();
        final String fid = intent.getStringExtra("fid");
        String name = intent.getStringExtra("name");
        title.setText(name);
        bought(fid);
        OkHttpDownloadJsonUtil.downloadJson(this, Path.FORUMBK(fid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Log.i("json", json);
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd")
                        .create();
                list = gson.fromJson(json, new TypeToken<List<BKBean>>() {
                }.getType());
                ForumBKLVAdapter adapter = new ForumBKLVAdapter(ForumBKActivity.this, list);
                lv.setAdapter(adapter);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (qx.equals("1")) {
                    String title = list.get(position).getSubject();
                    String name = list.get(position).getRealname();
                    String time = list.get(position).getDateline();
                    String pid = list.get(position).getPid();
                    String logo = list.get(position).getLogo();
                    String rep = list.get(position).getAlltip();
                    String authorid = list.get(position).getAuthorid();

                    OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), "http://shizhanzhe.com/index.php?m=pcdata.add_num&pc=1&uid=" + MyApplication.myid + "&pid=" + pid + "+&token=" + MyApplication.token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                        @Override
                        public void onsendJson(String json) {

                        }
                    });
                    Intent intent = new Intent(ForumBKActivity.this, ForumItemActivity.class);
                    intent.putExtra("pid", pid);
                    intent.putExtra("title", title);
                    intent.putExtra("name", name);
                    intent.putExtra("img", logo);
                    intent.putExtra("time", time);
                    intent.putExtra("rep", rep);
                    intent.putExtra("fid", fid);
                    intent.putExtra("authorid", authorid);

                    startActivity(intent);
                } else {
                    alertDialog.show();
                }
            }
        });
        puttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(ForumBKActivity.this, PostActivity.class);
                i.putExtra("fid", fid);
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

    //购买权限
    void bought(String fid) {
        OkHttpDownloadJsonUtil.downloadJson(this, "http://shizhanzhe.com/index.php?m=pcdata.quanxian&pc=1&fid=" + fid + "&uid=" + MyApplication.myid, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                if (json.contains("1")) {
                    qx = "1";
                }
            }
        });
    }
}
