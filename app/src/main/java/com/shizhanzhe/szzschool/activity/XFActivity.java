package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.shizhanzhe.szzschool.Bean.XFBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.XFAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by hasee on 2016/12/19.
 */
@ContentView(R.layout.activity_xf)
public class XFActivity extends Activity {
    @ViewInject(R.id.list_xf)
    ListView xf;
    @ViewInject(R.id.back)
    ImageView back;
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
        OkHttpDownloadJsonUtil.downloadJson(XFActivity.this, Path.XF(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                //Json的解析类对象
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(json).getAsJsonArray();
                final ArrayList<XFBean> list = new ArrayList<>();

                //加强for循环遍历JsonArray
                for (JsonElement xf : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    XFBean userBean = gson.fromJson(xf, XFBean.class);
                    list.add(userBean);
                }
                Collections.reverse(list);
                final XFAdapter xfAdapter = new XFAdapter(getApplicationContext(), list);
                xf.setAdapter(xfAdapter);
                xf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        new AlertDialog.Builder(XFActivity.this).setTitle("删除").setMessage("确定要删除本条记录吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String xfid = list.get(position).getId();
                                        OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), Path.DelXF(MyApplication.myid, xfid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                                            @Override
                                            public void onsendJson(String json) {
                                                    Log.i("===","删除成功"+json);
                                            }
                                        });
                                        list.remove(position);
                                        xfAdapter.notifyDataSetChanged();
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                    }
                });
            }
        });
    }
}
