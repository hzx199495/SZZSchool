package com.shizhanzhe.szzschool.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.shizhanzhe.szzschool.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by hasee on 2016/10/26.
 */
@ContentView(R.layout.fragment_my)
public class FragmentMy extends Fragment {
    @ViewInject(R.id.fragment_tv)
    private TextView tv;
    @ViewInject(R.id.fragment_img)
    private ImageView iv;
    @ViewInject(R.id.fragment_vip)
    private TextView tv_vip;
    @ViewInject(R.id.zhanghu)
    private TextView zh;
    @ViewInject(R.id.nicheng)
    private TextView nc;
    @ViewInject(R.id.uid)
    private TextView id;
    public static FragmentMy newInstance(String username,String img,String vip,String uid) {

        Bundle args = new Bundle();
        args.putString("username",username);
        args.putString("img",img);
        args.putString("vip",vip);
        args.putString("uid",uid);
        FragmentMy fragment = new FragmentMy();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String username = bundle.getString("username");
        String img = bundle.getString("img");
        String vip = bundle.getString("vip");
        String uid = bundle.getString("uid");
//        Log.i("===",username+"====" + img+"======="+vip);
        if(Integer.parseInt(vip)==0){
            tv_vip.setText("普通用户");
            tv_vip.setTextColor(Color.BLACK);
        }
        tv.setText(username);
        zh.setText(username);
        nc.setText(username);
        id.setText(uid);
        x.image().bind(iv,img);;
    }

}
