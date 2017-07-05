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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.CollectListBean;
import com.shizhanzhe.szzschool.Bean.ProBean2;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
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

    public static FragmentDetail newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id", id);
        FragmentDetail fragment = new FragmentDetail();
        fragment.setArguments(args);
        return fragment;
    }

    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setMessage("正在加载...Loading");

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
        dialog.show();
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        MyApplication.txId=id;
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.COLLECTLIST(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                List<CollectListBean> list = gson.fromJson(json, new TypeToken<List<CollectListBean>>() {
                }.getType());
                for (CollectListBean bean : list
                        ) {

                    if (bean.getSysinfo().get(0).getId().equals(id)) {
                        flag = true;
                        collect.setImageResource(R.drawable.ic_courseplay_star1);
                    }
                }
            }
        });
        OkHttpDownloadJsonUtil.downloadJson(getContext(), Path.SECOND(id, MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                MyApplication.videojson = json;
                Gson gson = new Gson();
                ProBean2.TxBean tx = gson.fromJson(json, ProBean2.class).getTx();
                detail_title.setText(tx.getStitle());
                detail_intro.setText(tx.getIntroduce());
                ImageLoader imageloader = ImageLoader.getInstance();
                imageloader.displayImage(Path.IMG(tx.getThumb()), detail_iv, MyApplication.displayoptions);
                MyApplication.proimg = tx.getThumb();
                detail_price.setText("￥" + tx.getNowprice());
                detail_study.setText("学习人数：" + tx.getNum() + "人");
                dialog.dismiss();
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

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();
// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(title);
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://shizhanzhe.com");
// text是分享文本，所有平台都需要这个字段
        oks.setText(intro);
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(Path.IMG(img));//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://shizhanzhe.com");

// 启动分享GUI
        oks.show(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();

        if (flag) {
            OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.COLLECT(MyApplication.myid, id, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {
                }
            });
        } else {
            OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.DELCOLLECT(MyApplication.myid, id, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {
                }
            });
        }
    }
}
