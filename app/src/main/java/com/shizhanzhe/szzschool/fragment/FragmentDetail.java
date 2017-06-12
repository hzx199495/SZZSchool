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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.CollectListBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.db.DatabaseOpenHelper;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.DbManager;
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
    @ViewInject(R.id.share)
    ImageView share;
    @ViewInject(R.id.collect)
    ImageView collect;

    public static FragmentDetail newInstance(String id, String img, String title, String intro, String price) {

        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("img", img);
        args.putString("title", title);
        args.putString("intro", intro);
        args.putString("price", price);
        FragmentDetail fragment = new FragmentDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, null);
    }

    String title;
    String intro;
    String img;
    String id;
    boolean flag;
    String tag;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.COLLECTLIST(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

            @Override
            public void onsendJson(String json) {

                JsonParser parser = new JsonParser();

                JsonArray jsonArray = parser.parse(json).getAsJsonArray();

                Gson gson = new Gson();
                ArrayList<List<CollectListBean.SysinfoBean>> sysinfo = new ArrayList<>();
                ArrayList<String> listId = new ArrayList<>();

                //加强for循环遍历JsonArray
                for (JsonElement pro : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    CollectListBean Bean = gson.fromJson(pro, CollectListBean.class);
                    sysinfo.add(Bean.getSysinfo());
                    listId.add(Bean.getId());
                }
                for (int i = 0; i < listId.size(); i++) {
                    List<CollectListBean.SysinfoBean> sysinfoBeen = sysinfo.get(i);
                    for (int j = 0; j < sysinfoBeen.size(); j++) {
                        if (sysinfoBeen.get(j).getId().equals(id)) {
                            flag = true;
                            collect.setImageResource(R.drawable.ic_courseplay_star1);
                            tag = listId.get(i);
                        } else {
                            flag = false;
                            collect.setImageResource(R.drawable.ic_courseplay_star2);
                        }
                    }
                }
            }
        });

        img = bundle.getString("img");
        title = bundle.getString("title");
        intro = bundle.getString("intro");
        String price = bundle.getString("price");
        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage(Path.IMG(img), detail_iv, MyApplication.displayoptions);
        detail_title.setText(title);
        detail_intro.setText(intro);
        detail_price.setText("￥" + price);
        share.setOnClickListener(this);
        collect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share:
                showShare();
                break;
            case R.id.collect:
                if (flag) {
                    flag = false;
                    collect.setImageResource(R.drawable.ic_courseplay_star2);
                    Toast.makeText(getActivity(), "取消收藏", Toast.LENGTH_SHORT).show();
                } else {
                    flag = true;
                    collect.setImageResource(R.drawable.ic_courseplay_star1);
                    Toast.makeText(getActivity(), "已收藏", Toast.LENGTH_SHORT).show();
                    break;
                }
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
        if (flag){
            OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.COLLECT(MyApplication.myid, id, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {

                }
            });
        }else {
            OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.DELCOLLECT(MyApplication.myid,tag, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {

                }
            });
        }
    }
}
