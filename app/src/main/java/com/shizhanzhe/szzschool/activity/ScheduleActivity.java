package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.ScheduleAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2017/8/16.
 * 进度分类
 */
@ContentView(R.layout.activity_schedule)
public class ScheduleActivity extends Activity {
    @ViewInject(R.id.schedule_lv)
    ListView lv;
    @ViewInject(R.id.back)
    ImageView back;

    private List<ProBean.TxBean> list;
    private ArrayList<String> arrayList;
    @ViewInject(R.id.empty)
    QMUIEmptyView empty;
    private QMUITipDialog dialog;
    private QMUITipDialog nobuy;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    nobuy.dismiss();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        dialog = new QMUITipDialog.Builder(this).setIconType(1).setTipWord("正在加载").create();
        getData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                dialog.show();
                SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
                final String vip = preferences.getString("vip", "");
                OkHttpDownloadJsonUtil.downloadJson(ScheduleActivity.this, new Path(ScheduleActivity.this).SECOND(list.get(position).getId()), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        try {
                            dialog.dismiss();
                            Gson gson = new Gson();
                            ProDeatailBean.TxBean tx = gson.fromJson(json, ProDeatailBean.class).getTx();
                            String isbuy = tx.getIsbuy();
                            if (vip.equals("1") || isbuy.equals("1")) {
                                Intent intent = new Intent(ScheduleActivity.this, ScheduleDeatilActivity.class);
                                intent.putExtra("name", arrayList.get(position));
                                intent.putExtra("id", list.get(position).getId());
                                intent.putExtra("json", json);
                                startActivity(intent);
                            } else {
                                nobuy=new QMUITipDialog.Builder(ScheduleActivity.this).setIconType(4).setTipWord("未购买该课程").create();
                                nobuy.show();
                                mhandler.sendEmptyMessageDelayed(1, 1500);
                            }
                        } catch (Exception e) {
                        }
                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void getData() {
 dialog.show();
        OkHttpDownloadJsonUtil.downloadJson(this, new Path(this).CENTER(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
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
                            Gson gson = new Gson();
                            list = gson.fromJson(json, ProBean.class).getTx();
                            arrayList = new ArrayList<>();
                            for (ProBean.TxBean bean : list
                                    ) {
                                arrayList.add(bean.getStitle());
                            }
                            if (arrayList.size() == 0) {

                            } else {
                                lv.setAdapter(new ScheduleAdapter(ScheduleActivity.this, arrayList));
                            }
                            dialog.dismiss();
                        } catch (Exception e) {
                            dialog.dismiss();
                            empty.show(false, "", "数据异常", "重试", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getData();
                                }
                            });
                        }
                    }
                }
        );
    }

}
