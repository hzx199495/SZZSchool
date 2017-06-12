package com.shizhanzhe.szzschool.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static com.shizhanzhe.szzschool.activity.MyApplication.displayoptions;

/**
 * Created by zz9527 on 2017/3/13.
 */
@ContentView(R.layout.fragment_tg_kt)
public class TGOpenFragment extends Fragment {
    @ViewInject(R.id.img)
    ImageView img;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.time)
    TextView time;
    @ViewInject(R.id.intro)
    TextView intro;
    @ViewInject(R.id.state)
    TextView state;


    public static TGOpenFragment newInstance(String title, String img, String time,String intro,String state) {

        Bundle args = new Bundle();
        args.putString("title",title);
        args.putString("img",img);
        args.putString("time",time);
        args.putString("state",state);
        args.putString("intro",intro);
        TGOpenFragment fragment = new TGOpenFragment();
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
        String titleText = bundle.getString("title");
        String img2=bundle.getString("img");
        String timeText=bundle.getString("time");
        String stateText=bundle.getString("state");
        String introText=bundle.getString("intro");

        title.setText(titleText);
        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage(Path.IMG(img2), img, displayoptions);
        time.setText(timeText);
        intro.setText(introText);
        state.setText(stateText);
    }
}
