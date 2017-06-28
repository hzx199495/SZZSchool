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

import java.util.ArrayList;

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
    @ViewInject(R.id.price1)
    TextView price1;
    @ViewInject(R.id.price2)
    TextView price2;
    @ViewInject(R.id.price3)
    TextView price3;
    @ViewInject(R.id.num1)
    TextView num1;
    @ViewInject(R.id.num2)
    TextView num2;
    @ViewInject(R.id.num3)
    TextView num3;
    @ViewInject(R.id.ktprice)
    TextView ktprice;

    public static TGOpenFragment newInstance(String title, String img, String time,String intro,String state,String ktprice,String tgprice) {

        Bundle args = new Bundle();
        args.putString("title",title);
        args.putString("img",img);
        args.putString("time",time);
        args.putString("ktprice",ktprice);
        args.putString("tgprice",tgprice);
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
        String ktmoney=bundle.getString("ktprice");
        String tgprice=bundle.getString("tgprice");
        title.setText(titleText);
        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage(Path.IMG(img2), img, displayoptions);
        time.setText(timeText);
        intro.setText(introText);
        state.setText(stateText);
        ktprice.setText("开团需缴纳保底金额"+ktmoney+"元，如开团成功将返回到您的账户，反之将不予返回！ ");
        ArrayList<String> pricelist = new ArrayList<>();
        ArrayList<String> numlist = new ArrayList<>();
        String[]  strs=tgprice.split("\\|");
        for (int i=0;i<strs.length;i++) {
            String[] strs2 = strs[i].split("-");
            numlist.add(strs2[0]);
            pricelist.add(strs2[1]);
        }
        price1.setText("￥"+pricelist.get(0));
        price2.setText("￥"+pricelist.get(1));
        price3.setText("￥"+pricelist.get(2));
        num1.setText("满"+numlist.get(0)+"人参团即可优惠至"+pricelist.get(0)+"元");
        num2.setText("满"+numlist.get(1)+"人参团即可优惠至"+pricelist.get(1)+"元");
        num3.setText("满"+numlist.get(2)+"人参团即可优惠至"+pricelist.get(2)+"元");
    }
}
