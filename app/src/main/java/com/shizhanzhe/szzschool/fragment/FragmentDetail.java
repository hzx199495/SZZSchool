package com.shizhanzhe.szzschool.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.CollectListBean;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by hasee on 2016/11/23.
 */
@ContentView(R.layout.fragment_detail1)
public class FragmentDetail extends Fragment implements View.OnClickListener {
    @ViewInject(R.id.detail_iv)
    ImageView detail_iv;
    @ViewInject(R.id.detail_title)
    TextView detail_title;
    @ViewInject(R.id.detail_intro)
    TextView detail_intro;
    @ViewInject(R.id.detail_price)
    TextView detail_price;
    @ViewInject(R.id.collect)
    ImageView collect;
    @ViewInject(R.id.detail_study)
    TextView detail_study;
    SVProgressHUD mSVProgressHUD;
    public static FragmentDetail newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id", id);
        FragmentDetail fragment = new FragmentDetail();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatus("加载中...");

        return x.view().inject(this, inflater, null);
    }

    String title;
    String intro;
    String img;
    String id;
    boolean flag;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSVProgressHUD.show();
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        MyApplication.txId=id;
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), new Path(getContext()).COLLECTLIST(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                List<CollectListBean> list = gson.fromJson(json, new TypeToken<List<CollectListBean>>() {
                }.getType());
                if (list.size()>0) {
                    for (CollectListBean bean : list
                            ) {
                        if (bean.getSysinfo().get(0).getId().contains(id)) {
                            flag = true;
                            collect.setImageResource(R.drawable.ic_courseplay_star1);
                        }
                    }
                }
            }
        });
        OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).SECOND(id), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                MyApplication.videojson = json;
                Gson gson = new Gson();
                ProDeatailBean.TxBean tx = gson.fromJson(json, ProDeatailBean.class).getTx();
                detail_title.setText(tx.getStitle());
                detail_intro.setText(tx.getIntroduce());
                ImageLoader imageloader = ImageLoader.getInstance();
                imageloader.displayImage(Path.IMG(tx.getThumb()), detail_iv, MyApplication.displayoptions);
                MyApplication.proimg = tx.getThumb();
                detail_price.setText("￥" + tx.getNowprice());
                detail_study.setText("学习人数：" + tx.getNum() + "人");
                mSVProgressHUD.dismiss();
            }
        });

        collect.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.collect:
                if (flag) {
                    flag = false;
                    collect.setImageResource(R.drawable.ic_courseplay_star2);
                    Toast.makeText(getActivity(), "取消收藏", Toast.LENGTH_SHORT).show();
                } else {
                    flag = true;
                    collect.setImageResource(R.drawable.ic_courseplay_star1);
                    Toast.makeText(getActivity(), "已收藏", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }


    @Override
    public void onStop() {
        super.onStop();

        if (flag) {
            OkHttpDownloadJsonUtil.downloadJson(getActivity(), new Path(getContext()).COLLECT(id), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {
                }
            });
        } else {
            OkHttpDownloadJsonUtil.downloadJson(getActivity(), new Path(getContext()).DELCOLLECT(id), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {
                }
            });
        }
    }
}
