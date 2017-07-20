package com.shizhanzhe.szzschool.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.MyKTBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

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
    @ViewInject(R.id.percent)
    TextView percent;

    public static TGOpenFragment newInstance(String title, String img, String time, String intro, String ktprice, String tgprice, String kfm) {

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("img", img);
        args.putString("time", time);
        args.putString("ktprice", ktprice);
        args.putString("tgprice", tgprice);
        args.putString("intro", intro);
        args.putString("kfm", kfm);

        TGOpenFragment fragment = new TGOpenFragment();
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
        KT();
    }
    void initView(){
        Bundle bundle = getArguments();
        String titleText = bundle.getString("title");
        String img2 = bundle.getString("img");
        String timeText = bundle.getString("time");
        String introText = bundle.getString("intro");
        String ktmoney = bundle.getString("ktprice");
        String tgprice = bundle.getString("tgprice");
        String kfm = bundle.getString("kfm");
        percent.setText("开团成功福利，可获得团总交易额的" + kfm + "%！");
        title.setText(titleText);
        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage(Path.IMG(img2), img, displayoptions);
        time.setText(timeText);
        intro.setText(introText);
        ktprice.setText("开团需缴纳保底金额" + ktmoney + "元，如开团成功将返回到您的账户，反之将不予返回！ ");
        ArrayList<String> pricelist = new ArrayList<>();
        ArrayList<String> numlist = new ArrayList<>();
        String[] strs = tgprice.split("\\|");
        for (int i = 0; i < strs.length; i++) {
            String[] strs2 = strs[i].split("-");
            numlist.add(strs2[0]);
            pricelist.add(strs2[1]);
        }
        price1.setText("￥" + pricelist.get(0));
        price2.setText("￥" + pricelist.get(1));
        price3.setText("￥" + pricelist.get(2));
        num1.setText("满" + numlist.get(0) + "人参团即可优惠至" + pricelist.get(0) + "元");
        num2.setText("满" + numlist.get(1) + "人参团即可优惠至" + pricelist.get(1) + "元");
        num3.setText("满" + numlist.get(2) + "人参团即可优惠至" + pricelist.get(2) + "元");
        dialog.dismiss();
    }
    void KT() {
        SharedPreferences preferences =getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
         String uid = preferences.getString("uid", "");
         String token = preferences.getString("token", "");
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), "https://shizhanzhe.com/index.php?m=pcdata.mykaituan&pc=1&uid=" + uid + "&token=" + token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                List<MyKTBean> list = gson.fromJson(json, new TypeToken<List<MyKTBean>>() {
                }.getType());
                if (list.size() > 0) {
                    for (MyKTBean bean :
                            list) {
                        if (bean.getTuanid().contains(MyApplication.tuanid)) {
                            state.setText(bean.getUname()+"的学习团，已参团"+bean.getTynum()+"人");
                        }

                    }
                }else {
                    state.setText("未开团");
                }
                initView();
            }
        });
    }

}
