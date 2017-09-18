package com.shizhanzhe.szzschool.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.SearchBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.SearchAdapter;
import com.shizhanzhe.szzschool.utils.MyListView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 搜索
 */
@ContentView(R.layout.activity_search)
public class SearchActivity extends Activity {
@ViewInject(R.id.search_et_input)
    EditText et;
    @ViewInject(R.id.search_btn_back)
    Button back;
    @ViewInject(R.id.kc)
    MyListView kc;
    @ViewInject(R.id.lt)
    MyListView lt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!"".equals(et.getText().toString().trim())){
                    OkHttpDownloadJsonUtil.downloadJson(SearchActivity.this, Path.SEARCH(et.getText().toString().trim()), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                        @Override
                        public void onsendJson(String json) {
                            Gson gson = new Gson();
                            final List<SearchBean.TxBean> tx = gson.fromJson(json, SearchBean.class).getTx();
                            final List<SearchBean.TzBean> tz = gson.fromJson(json, SearchBean.class).getTz();
                            kc.setAdapter(new SearchAdapter(getApplicationContext(),tx,null));
                            lt.setAdapter(new SearchAdapter(getApplicationContext(),null,tz));
                            if (tx.size()==0&&tz.size()==0){
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
                                    }else {
                                        Toast.makeText(SearchActivity.this,"请先登录！", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SearchActivity.this, LoginActivity.class));
                                    }
                                }
                            });
                        }
                    });
                }

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