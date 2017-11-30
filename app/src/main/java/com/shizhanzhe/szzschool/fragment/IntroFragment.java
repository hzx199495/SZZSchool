package com.shizhanzhe.szzschool.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.utils.Path;

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

    public static IntroFragment newInstance(String sid,String imgs) {

        Bundle args = new Bundle();
        args.putString("sid", sid);
        args.putString("imgs", imgs);
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
        String imgs = getArguments().getString("imgs");
        String[] split = imgs.split(",");
        if (split.length==1){
            ImageLoader.getInstance().displayImage(Path.IMG(split[0]), iv, MyApplication.displayoptions);
        }else if (split.length==2){
            iv2.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Path.IMG(split[0]), iv, MyApplication.displayoptions);
            ImageLoader.getInstance().displayImage(Path.IMG(split[1]), iv2, MyApplication.displayoptions);
        }else if (split.length==3){
            iv2.setVisibility(View.VISIBLE);
            iv3.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Path.IMG(split[0]), iv, MyApplication.displayoptions);
            ImageLoader.getInstance().displayImage(Path.IMG(split[1]), iv2, MyApplication.displayoptions);
            ImageLoader.getInstance().displayImage(Path.IMG(split[2]), iv3, MyApplication.displayoptions);
        }
    }
}
