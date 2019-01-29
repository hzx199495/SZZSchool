package com.shizhanzhe.szzschool.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.SearchBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.SearchAdapter;
import com.shizhanzhe.szzschool.utils.MyListView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.widge.FlowLayout;

import org.w3c.dom.Text;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索
 */
@ContentView(R.layout.activity_search)
public class SearchActivity extends Activity {
    @ViewInject(R.id.search_et_input)
    EditText et;
    @ViewInject(R.id.search_btn_back)
    TextView back;
    @ViewInject(R.id.kc)
    MyListView kc;
    @ViewInject(R.id.lt)
    MyListView lt;
    @ViewInject(R.id.scroll)
    ScrollView scrollView;
    @ViewInject(R.id.flowLayout)
    FlowLayout flowLayout;
    @ViewInject(R.id.flowLayout_history)
    FlowLayout flowLayout_history;
    @ViewInject(R.id.histroy)
    LinearLayout histroyview;
    @ViewInject(R.id.search_delete)
    ImageView search_delete;
    private String[] mLabels = {"推广", "淘客", "微电商", "微信", "PHP", "Android", "IOS",};
    private SharedPreferences.Editor editor;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(ContextCompat.getColor(this, R.color.top));
        initLabel();
        SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
        final String vip = preferences.getString("vip", "");
        final String uid = preferences.getString("uid", "");
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!"".equals(et.getText().toString().trim())) {
                    if (!history.contains(et.getText().toString().trim())){
                        StringBuilder builder = new StringBuilder(history);
                        builder.append("," + et.getText().toString().trim());
                        editor.putString("history",builder.toString());
                        editor.commit();
                    }
                    scrollView.setVisibility(View.VISIBLE);
                    OkHttpDownloadJsonUtil.downloadJson(SearchActivity.this, Path.SEARCH(et.getText().toString().trim()), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

                        @Override
                        public void onsendJson(String json) {

                            try {
                                Gson gson = new Gson();

                                final List<SearchBean.TxBean> tx = gson.fromJson(json, SearchBean.class).getTx();
                                final List<SearchBean.TzBean> tz = gson.fromJson(json, SearchBean.class).getTz();
                                kc.setAdapter(new SearchAdapter(getApplicationContext(), tx, null));
                                lt.setAdapter(new SearchAdapter(getApplicationContext(), null, tz));
                                if (tx.size() == 0 && tz.size() == 0) {
                                    Toast.makeText(SearchActivity.this, "无搜索结果", Toast.LENGTH_SHORT).show();
                                }
                                kc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent();
                                        intent.setClass(SearchActivity.this, DetailActivity.class);
                                        String title = tx.get(position).getStitle();
                                        String img = tx.get(position).getThumb();
                                        String intro = tx.get(position).getIntroduce();
                                        String proid = tx.get(position).getId();
                                        String price = tx.get(position).getNowprice();
                                        intent.putExtra("id", proid);
                                        intent.putExtra("img", img);
                                        intent.putExtra("title", title);
                                        intent.putExtra("intro", intro);
                                        intent.putExtra("price", price);
                                        startActivity(intent);
                                    }
                                });
                                lt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        if (MyApplication.isLogin) {
                                            if (vip.equals("1")) {
                                                String title = tz.get(position).getSubject();
                                                String name = tz.get(position).getRealname();
                                                String time = tz.get(position).getDateline();
                                                String pid = tz.get(position).getPid();
                                                String logo = tz.get(position).getLogo();
                                                String rep = tz.get(position).getAlltip();
                                                String fid = tz.get(position).getFid();

                                                Intent intent = new Intent(SearchActivity.this, ForumItemActivity.class);
                                                intent.putExtra("pid", pid);
                                                intent.putExtra("title", title);
                                                intent.putExtra("name", name);
                                                intent.putExtra("img", logo);
                                                intent.putExtra("time", time);
                                                intent.putExtra("rep", rep);
                                                intent.putExtra("fid", fid);
                                                startActivity(intent);
                                            }else{
                                                bought(tz.get(position).getFid(),uid,tz.get(position));
                                            }
                                        } else {
                                            Toast.makeText(SearchActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SearchActivity.this, LoginActivity.class));
                                        }
                                    }
                                });
                            } catch (Exception e) {

                            }
                        }
                    });
                }

            }
        });
        search_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                histroyview.setVisibility(View.GONE);
                flowLayout_history.setVisibility(View.GONE);
                editor.clear();
                editor.commit();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    String history;
    // 初始化标签
    private void initLabel() {
        SharedPreferences preferences = getSharedPreferences("search", Context.MODE_PRIVATE);
        editor = preferences.edit();
        history = preferences.getString("history","");
        if ("".equals(history)){

        }else {
            histroyview.setVisibility(View.VISIBLE);
            flowLayout_history.setVisibility(View.VISIBLE);
            final String[] split = history.split(",");
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(30, 10, 30, 0);// 设置边距
            for (int i = 1; i < split.length; i++) {
                final TextView textView = new TextView(this);
                textView.setTag(i);
                textView.setTextSize(14);
                textView.setText(split[i]);
                textView.setPadding(20, 8, 20, 8);
                textView.setBackgroundResource(R.drawable.lable_item_bg_normal);
                flowLayout_history.addView(textView, layoutParams);
                // 标签点击事件
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et.setText(split[(int) textView.getTag()]);
                    }
                });
            }
        }
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 30, 10, 10);// 设置边距
        for (int i = 0; i < mLabels.length; i++) {
            final TextView textView = new TextView(this);
            textView.setTag(i);
            textView.setTextSize(14);
            textView.setText(mLabels[i]);
            textView.setPadding(20, 8, 20, 8);
            textView.setBackgroundResource(R.drawable.lable_item_bg_normal);
            flowLayout.addView(textView, layoutParams);
            // 标签点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et.setText(mLabels[(int) textView.getTag()]);
                }
            });
        }
    }
    //购买判断
    void bought(String fid, String uid, final SearchBean.TzBean tz) {
        OkHttpDownloadJsonUtil.downloadJson(SearchActivity.this, "https://shizhanzhe.com/index.php?m=pcdata.quanxian&pc=1&fid=" + fid + "&uid=" + uid, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                if (json.contains("1")) {
                    String title = tz.getSubject();
                    String name = tz.getRealname();
                    String time = tz.getDateline();
                    String pid = tz.getPid();
                    String logo = tz.getLogo();
                    String rep = tz.getAlltip();
                    String fid = tz.getFid();
                    Intent intent = new Intent(SearchActivity.this, ForumItemActivity.class);
                    intent.putExtra("pid", pid);
                    intent.putExtra("title", title);
                    intent.putExtra("name", name);
                    intent.putExtra("img", logo);
                    intent.putExtra("time", time);
                    intent.putExtra("rep", rep);
                    intent.putExtra("fid", fid);
                    startActivity(intent);
                }else {
                    mdialog = new QMUITipDialog.Builder(SearchActivity.this).setIconType(4).setTipWord("课程未购买").create();
                    mdialog.show();
                    mhandler.sendEmptyMessageDelayed(1,1500);
                }
            }
        });
    }
}