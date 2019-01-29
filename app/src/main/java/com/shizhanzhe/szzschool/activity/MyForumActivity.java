package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.ForumBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.ScheduleAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.StatusBarUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2017/9/15.
 */
@ContentView(R.layout.activity_myforum)
public class MyForumActivity extends Activity {
    @ViewInject(R.id.myforum_lv)
    ListView lv;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.empty)
    QMUIEmptyView empty;
    private QMUITipDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setStatusBarColor(this,R.color.white); }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog = new QMUITipDialog.Builder(this).setIconType(1).setTipWord("正在加载").create();
        getData();
    }
    void getData(){
        dialog.show();
        OkHttpDownloadJsonUtil.downloadJson(this, Path.FORUMHOME(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                try {
                    if (json.equals("0")) {
                        dialog.dismiss();
                        empty.show(false, "", "网络异常", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData();
                            }
                        });
                        return;
                    } else if (json.equals("1")) {
                        dialog.dismiss();
                        empty.show(false, "", "网络超时", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData();
                            }
                        });
                        return;
                    }
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd")
                            .create();
                    final List<ForumBean.LtmodelBean> list = gson.fromJson(json, ForumBean.class).getLtmodel();
                    final ArrayList<String> arrayList = new ArrayList<>();
                    for (ForumBean.LtmodelBean bean : list
                            ) {
                        arrayList.add(bean.getName());
                    }
                    if (arrayList.size()==0){
                        empty.show("", "暂无数据");
                    }else {
                        lv.setAdapter(new ScheduleAdapter(MyForumActivity.this, arrayList));

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(MyForumActivity.this, MyForumDetailActivity.class);
                                intent.putExtra("title", list.get(position).getName());
                                intent.putExtra("fid", list.get(position).getFid());
                                startActivity(intent);
                            }
                        });
                    }
                    dialog.dismiss();
                }catch (Exception e){
                    dialog.dismiss();
                    empty.show(false, "", "数据异常", "重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getData();
                        }
                    });
                }

            }
        });
    }
}
