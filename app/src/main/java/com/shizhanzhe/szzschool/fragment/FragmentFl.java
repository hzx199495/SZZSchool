package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.adapter.GVAdapter;
import com.shizhanzhe.szzschool.adapter.ListAdapter;
import com.shizhanzhe.szzschool.adapter.MyAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2016/11/7.
 */
@ContentView(R.layout.fragment_fl)
public class FragmentFl extends Fragment implements AdapterView.OnItemClickListener {

    @ViewInject(R.id.lv_title)
    ListView lv_title;

    private String uid;
    private String token;
    private ArrayList<ProBean> gvlist;
    private MyAdapter adapter;
    private MyFragment myFragment;
    private String[] strs = {"课程体系", "进阶课程", "职业课程", "推荐课程"};
    public static int mPosition;

    public static FragmentFl newInstance(String uid, String token) {

        Bundle args = new Bundle();
        args.putString("uid", uid);
        args.putString("token", token);
        FragmentFl fragment = new FragmentFl();
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
        adapter = new MyAdapter(getContext(), strs);
        lv_title.setAdapter(adapter);
        lv_title.setOnItemClickListener(this);

        //创建MyFragment对象
        myFragment = new MyFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, myFragment);
        //通过bundle传值给MyFragment
        Bundle bundle = new Bundle();
        bundle.putString(MyFragment.TAG, strs[mPosition]);
        myFragment.setArguments(bundle);
        fragmentTransaction.commit();
        Bundle bundle2 = getArguments();
        final String uid = bundle2.getString("uid");
        final String token = bundle2.getString("token");
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.CLASSIFY(uid, token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                gvlist = gson.fromJson(json, new TypeToken<List<ProBean>>() {
                }.getType());
            }
        });
//        ArrayList<String> list = new ArrayList<>();
//        list.add("课程体系");
//        list.add("进阶课程");
//        list.add("职业课程");
//        list.add("推荐课程");
//        lv_title.setAdapter(new ListAdapter(getActivity(), list));
//        lv_title.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("====", position + "");
//                switch (position) {
//                    case 0:
//                        GVAdapter adapter = new GVAdapter(gvlist, getActivity());
//                        fl_gv.setAdapter(adapter);
//                        fl_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Intent intent = new Intent();
//                                intent.setClass(getActivity(), DetailActivity.class);
//
//                                String title=gvlist.get(position).getStitle();
//                                String img=gvlist.get(position).getThumb();
//                                String intro=gvlist.get(position).getIntroduce();
//                                String catid = gvlist.get(position).getCatid();
//                                intent.putExtra("id",catid);
//                                intent.putExtra("uid",uid);
//                                intent.putExtra("token",token);
//                                intent.putExtra("img",img);
//                                intent.putExtra("title",title);
//                                intent.putExtra("intro",intro);
//                                startActivity(intent);
//                            }
//                        });
//                        break;
//                }
//
//            }
//
//
//        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //拿到当前位置
        mPosition = position;
        //即使刷新adapter
        adapter.notifyDataSetChanged();
        for (int i = 0; i < strs.length; i++) {
            myFragment = new MyFragment();
            FragmentTransaction fragmentTransaction = getChildFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, myFragment);

            fragmentTransaction.commit();
        }
    }
}


