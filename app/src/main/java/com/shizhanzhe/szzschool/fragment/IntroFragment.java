package com.shizhanzhe.szzschool.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by zz9527 on 2017/11/6.
 */
@ContentView(R.layout.fragment_intro)
public class IntroFragment extends android.support.v4.app.Fragment {
    @ViewInject(R.id.intro_tv)
    ImageView iv;
    @ViewInject(R.id.intro_tv2)
    ImageView iv2;
    @ViewInject(R.id.intro_tv3)
    ImageView iv3;

    public static IntroFragment newInstance(String sid) {

        Bundle args = new Bundle();
        args.putString("sid", sid);
        IntroFragment fragment = new IntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments().getString("sid").equals("89")) {
            iv2.setVisibility(View.VISIBLE);
            iv3.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage("http://shizhanzhe.com/img/tx89_1.jpg", iv, MyApplication.displayoptions);
            ImageLoader.getInstance().displayImage("http://shizhanzhe.com/img/tx89_2.jpg", iv2, MyApplication.displayoptions);
            ImageLoader.getInstance().displayImage("http://shizhanzhe.com/img/tx89_3.jpg", iv3, MyApplication.displayoptions);

        }else {

            ImageLoader.getInstance().displayImage("http://shizhanzhe.com/img/tx"+getArguments().getString("sid")+".jpg", iv, MyApplication.displayoptions);

        }
    }
}
