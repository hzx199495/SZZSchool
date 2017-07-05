package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.KTListBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.KTAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2017/3/14.
 */
@ContentView(R.layout.activity_ktlist)
public class KTListActivity extends Activity {
    @ViewInject(R.id.list)
    ListView lv;
    List<KTListBean> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        this.setFinishOnTouchOutside(true);
        Intent intent = getIntent();
        String tuanid = intent.getStringExtra("tuanid");
        final String img = intent.getStringExtra("img");
        final String tgtitle = intent.getStringExtra("title");
        String ctprice = intent.getStringExtra("ctprice");
        String[]  strs=ctprice.split("\\|");
        ArrayList<String> pricelist = new ArrayList<>();
        for (int i=0;i<strs.length;i++) {
            String[] strs2 = strs[i].split("-");
            pricelist.add(strs2[1]);
        }
        final String price=pricelist.get(pricelist.size()-1);
        OkHttpDownloadJsonUtil.downloadJson(this, "http://shizhanzhe.com/index.php?m=pcdata.kaituancha&pc=1&tid=" + tuanid, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                list = gson.fromJson(json, new TypeToken<List<KTListBean>>() {
                }.getType());
                lv.setAdapter(new KTAdapter(getApplicationContext(), list, tgtitle, img));

            }

        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(KTListActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确认支付￥"+price+"定金参团？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OkHttpDownloadJsonUtil.downloadJson(KTListActivity.this, "http://shizhanzhe.com/index.php?m=pcdata.cantuan&pc=1&ktid=" + list.get(position).getId() + "&uid=" + MyApplication.myid + "&token=" + MyApplication.token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                            @Override
                            public void onsendJson(String json) {
                                if (json.contains("0")) {
                                    Toast.makeText(KTListActivity.this, "无此开团", Toast.LENGTH_SHORT).show();
                                } else if (json.contains("1")) {
                                    Toast.makeText(KTListActivity.this, "参团成功", Toast.LENGTH_SHORT).show();
                                } else if (json.contains("2")) {
                                    Toast.makeText(KTListActivity.this, "数据库操作失败", Toast.LENGTH_SHORT).show();
                                } else if (json.contains("3")) {
                                    Toast.makeText(KTListActivity.this, "已经参团", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });
                builder.create().show();
            }
        });
    }
}