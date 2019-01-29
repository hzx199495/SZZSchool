package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.KTListBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.KTAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.StatusBarUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2017/3/14.
 * 开团列表
 */
@ContentView(R.layout.activity_ktlist)
public class KTListActivity extends Activity {
    @ViewInject(R.id.list)
    ListView lv;
    @ViewInject(R.id.back)
    ImageView back;
    private List<KTListBean> list;
    private QMUITipDialog mdialog;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mdialog.dismiss();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setStatusBarColor(this,R.color.white); }
        this.setFinishOnTouchOutside(true);
        SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
        final String uid = preferences.getString("uid", "");
        final String token = preferences.getString("token", "");
        Intent intent = getIntent();
        String tuanid = intent.getStringExtra("tuanid");
        final String img = intent.getStringExtra("img");
        final String tgtitle = intent.getStringExtra("title");
        String ctprice = intent.getStringExtra("ctprice");
        String[] strs = ctprice.split("\\|");
        ArrayList<String> pricelist = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            String[] strs2 = strs[i].split("-");
            pricelist.add(strs2[1]);
        }
        final String price = pricelist.get(pricelist.size() - 1);
        OkHttpDownloadJsonUtil.downloadJson(this, "https://shizhanzhe.com/index.php?m=pcdata.kaituancha&pc=1&tid=" + tuanid, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                if (json.equals("0")) {
                    Toast.makeText(KTListActivity.this, "暂无开团", Toast.LENGTH_SHORT).show();
//                    mdialog = new QMUITipDialog.Builder(KTListActivity.this).setIconType(4).setTipWord("暂无开团").create();
//                    mdialog.show();
//                    mhandler.sendEmptyMessageDelayed(1,1500);
                } else {
                    list = gson.fromJson(json, new TypeToken<List<KTListBean>>() {
                    }.getType());
                    lv.setAdapter(new KTAdapter(getApplicationContext(), list, tgtitle, img));
                }
            }

        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(KTListActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确认支付￥" + price + "定金参团？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OkHttpDownloadJsonUtil.downloadJson(KTListActivity.this, "https://shizhanzhe.com/index.php?m=pcdata.cantuan&pc=1&ktid=" + list.get(position).getId() + "&uid=" + uid + "&token=" + token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

                            @Override
                            public void onsendJson(String json) {
                                if (json.contains("0")) {
                                    Toast.makeText(KTListActivity.this, "无此开团", Toast.LENGTH_SHORT).show();
                                    mdialog = new QMUITipDialog.Builder(KTListActivity.this).setIconType(4).setTipWord("无此开团").create();
                                    mdialog.show();
                                    mhandler.sendEmptyMessageDelayed(1,1500);
                                } else if (json.contains("1")) {
                                    Toast.makeText(KTListActivity.this, "参团成功", Toast.LENGTH_SHORT).show();
                                    mdialog = new QMUITipDialog.Builder(KTListActivity.this).setIconType(4).setTipWord("参团成功").create();
                                    mdialog.show();
                                    mhandler.sendEmptyMessageDelayed(1,1500);
                                } else if (json.contains("2")) {
                                    mdialog = new QMUITipDialog.Builder(KTListActivity.this).setIconType(4).setTipWord("数据库操作失败").create();
                                    mdialog.show();
                                    mhandler.sendEmptyMessageDelayed(1,1500);
                                } else if (json.contains("3")) {
                                    mdialog = new QMUITipDialog.Builder(KTListActivity.this).setIconType(4).setTipWord("已经参团").create();
                                    mdialog.show();
                                    mhandler.sendEmptyMessageDelayed(1,1500);
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}