package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.Bean.THBean;
import com.shizhanzhe.szzschool.Bean.WLTG;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.adapter.THAdapter;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.widge.VpSwipeRefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zz9527 on 2018/7/10.
 */
@ContentView(R.layout.fragment_th)
public class THFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.gridview_th)
    MyGridView gv_th;
    @ViewInject(R.id.center_swip)
    VpSwipeRefreshLayout swip;
    @ViewInject(R.id.empty)
    QMUIEmptyView empty;
    @ViewInject(R.id.iv)
    ImageView iv;
    @ViewInject(R.id.tv)
    TextView tv;
    @ViewInject(R.id.th0)
    LinearLayout th0;
    private QMUITipDialog dialog;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            // 是否设置为圆角，弧度为多少，当弧度为90时显示的是一个圆
            .displayer(new RoundedBitmapDisplayer(15))
            .showImageOnLoading(R.drawable.img_load)
            .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
            .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
            .build();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new QMUITipDialog.Builder(getContext()).setIconType(1).setTipWord("正在加载").create();
        getData();
        swip.setOnRefreshListener(this);
        swip.setColorSchemeResources(R.color.blue2, R.color.red, R.color.green_color, R.color.dimgray);
    }

    public void getData() {
        OkHttpDownloadJsonUtil.downloadJson(getContext(), Path.TH(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                try {
                    if (json.equals("0")) {
                        dialog.dismiss();
                        empty.show(false, "", "网络异常", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData();
                            }
                        });
                        return;
                    } else if (json.equals("1")) {
                        dialog.dismiss();
                        empty.show(false, "", "网络超时", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData();
                            }
                        });
                        return;
                    }
                    Gson gson = new Gson();
                    final THBean thBean = gson.fromJson(json, THBean.class);
                    ImageLoader imageloader = ImageLoader.getInstance();
                    imageloader.displayImage(Path.IMG(thBean.getMian().get(0).getThumb()), iv, options);
                    tv.setText(thBean.getMian().get(0).getStitle());
                    th0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), DetailActivity.class);
                            String proid = thBean.getMian().get(0).getId();
                            intent.putExtra("id", proid);
                            startActivity(intent);
                        }
                    });
                    final List<THBean.ShowBean> show = thBean.getShow();
                    gv_th.setAdapter(new THAdapter(show, getContext()));
                    gv_th.setFocusable(false);
                    dialog.dismiss();
                    swip.setVisibility(View.VISIBLE);
                    gv_th.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), DetailActivity.class);
                            String proid = show.get(position).getId();
                            intent.putExtra("id", proid);
                            startActivity(intent);

                        }
                    });
                } catch (Exception e) {
                    dialog.dismiss();
                    empty.show(false, "", "数据异常", "重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getData();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                getData();
                swip.setRefreshing(false);
            }
        }, 2000);
    }
}
