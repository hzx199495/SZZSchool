package com.shizhanzhe.szzschool.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
 * Created by zz9527 on 2017/3/10.
 */
@ContentView(R.layout.fragment_tg_ct)
public class TGJoinFragment extends Fragment {
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.img)
    ImageView img;
    @ViewInject(R.id.time)
    TextView time;
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
    @ViewInject(R.id.intro)
    TextView intro;
    @ViewInject(R.id.end)
    TextView end;

    public static TGJoinFragment newInstance(String title, String img, String time, String price, String intro) {

        Bundle args = new Bundle();
        args.putString("title",title);
        args.putString("img",img);
        args.putString("time",time);
        args.putString("price",price);
        args.putString("intro",intro);
        TGJoinFragment fragment = new TGJoinFragment();
        fragment.setArguments(args);
        return fragment;
    }
    ProgressDialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialog = new ProgressDialog(getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setMessage("正在加载...Loading");
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog.show();
        Bundle bundle = getArguments();
        String titleText = bundle.getString("title");
        String img2=bundle.getString("img");
        String timeText=bundle.getString("time");
        String priceText=bundle.getString("price");
        String introText=bundle.getString("intro");

        title.setText(titleText);
        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage(Path.IMG(img2), img, displayoptions);
        time.setText(timeText);
        intro.setText(introText);
        end.setText("团购结束时间（"+timeText+"）起五天内，去个人中心团购页补齐尾款，逾期视为自动退团，资金转入个人学院账户余额");
        ArrayList<String> pricelist = new ArrayList<>();
        ArrayList<String> numlist = new ArrayList<>();
        String[]  strs=priceText.split("\\|");
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
        dialog.dismiss();
    }
}
