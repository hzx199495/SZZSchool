package com.shizhanzhe.szzschool.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by hasee on 2016/11/23.
 */
@ContentView(R.layout.fragment_detail1)
public class FragmentDetail extends Fragment {
    @ViewInject(R.id.detail_iv)
    ImageView detail_iv;
    @ViewInject(R.id.detail_title)
    TextView detail_title;
    @ViewInject(R.id.detail_intro)
    TextView detail_intro;
    @ViewInject(R.id.detail_price)
    TextView detail_price;
    public static FragmentDetail newInstance(String img, String title, String intro) {

        Bundle args = new Bundle();
        args.putString("img",img);
        args.putString("title",title);
        args.putString("intro",intro);
//        args.putString("price",price);
        FragmentDetail fragment = new FragmentDetail();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this,inflater,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
         String img = bundle.getString("img");
        String title = bundle.getString("title");
        String intro = bundle.getString("intro");
//        String price = bundle.getString("price");
        x.image().bind(detail_iv, Path.IMG(img));
        detail_title.setText(title);
        detail_intro.setText(intro);
//        detail_price.setText(price);

    }
}
