package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.MyCTBean;
import com.shizhanzhe.szzschool.Bean.MyKTBean;
import com.shizhanzhe.szzschool.Bean.TGsqlBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.adapter.MyCTAdapter;
import com.shizhanzhe.szzschool.adapter.MyKTAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import static com.shizhanzhe.szzschool.R.id.lv_kc;

/**
 * Created by zz9527 on 2017/6/13.
 */
@ContentView(R.layout.fragment_kc)
public class MyTGFragment extends Fragment {
    @ViewInject(R.id.gv)
    GridView gv;

    public static MyTGFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type",type);
        MyTGFragment fragment = new MyTGFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
         int type = bundle.getInt("type");
        if (type==1) {
            OkHttpDownloadJsonUtil.downloadJson(getContext(), Path.MYKT(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {
                    Gson gson = new Gson();
                    final List<MyKTBean> kclist = gson.fromJson(json, new TypeToken<List<MyKTBean>>() {
                    }.getType());

                    if (kclist != null && kclist.size() > 0) {
                        gv.setAdapter(new MyKTAdapter(getContext(), kclist));
//                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            try {
//                                List<TGsqlBean> proid = manager.selector(TGsqlBean.class).where("tuanid", "=", list.get(position).getTuanid()).findAll();
//                                Intent intent = new Intent();
//                                intent.setClass(getContext(), TGDetailActivity.class);
//                                intent.putExtra("title", proid.get(0).getTitle());
//                                intent.putExtra("img", proid.get(0).getImg());
//                                intent.putExtra("time", proid.get(0).getTime());
//                                intent.putExtra("intro", proid.get(0).getIntro());
//                                intent.putExtra("yjprice", proid.get(0).getYjprice());
//                                intent.putExtra("id", proid.get(0).getId());
//                                intent.putExtra("tuanid", proid.get(0).getTuanid());
//                                intent.putExtra("price", "100");
//                                intent.putExtra("type", 1);
//                                getContext().startActivity(intent);
//                            } catch (DbException e) {
//                                e.printStackTrace();
//                            }

//                        }
//                    });
//                }


                    }
                }
            });
        }else if (type==2) {
            OkHttpDownloadJsonUtil.downloadJson(getContext(), Path.MYCT(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {
                    Gson gson = new Gson();
                    final List<MyCTBean> ctlist = gson.fromJson(json, new TypeToken<List<MyCTBean>>() {
                    }.getType());
                    if (ctlist != null && ctlist.size() > 0) {
                        gv.setAdapter(new MyCTAdapter(getContext(), ctlist));
                    }
                }
            });
        }
    }
}
