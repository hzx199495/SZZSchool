package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.MyCTBean;
import com.shizhanzhe.szzschool.Bean.MyKTBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.TGDetailActivity;
import com.shizhanzhe.szzschool.adapter.MyCTAdapter;
import com.shizhanzhe.szzschool.adapter.MyKTAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.widge.VpSwipeRefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zz9527 on 2017/6/13.
 */
@ContentView(R.layout.fragment_kc)
public class MyTGFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.gv)
    GridView gv;
    @ViewInject(R.id.swipe_container)
    SwipeRefreshLayout swip;
    private int type;
//    @ViewInject(R.id.state_layout)
//    StateLayout state_layout;
//    @ViewInject(R.id.ll)
//    LinearLayout ll;
@ViewInject(R.id.empty)
QMUIEmptyView empty;
    @ViewInject(R.id.iv)
    ImageView iv;
    private QMUITipDialog dialog;
    public static MyTGFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        MyTGFragment fragment = new MyTGFragment();
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
        dialog = new QMUITipDialog.Builder(getContext()).setIconType(1).setTipWord("正在加载").create();
        Bundle bundle = getArguments();
        type = bundle.getInt("type");
        getData(type);
        gv.setEmptyView(iv);
        swip.setOnRefreshListener(this);
        swip.setColorSchemeResources(R.color.blue2, R.color.red, R.color.green_color, R.color.dimgray);
    }


    void getData(int type) {
        dialog.show();
        if (type == 1) {
            OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).MYKT(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

                @Override
                public void onsendJson(String json) {
                    try {
                        if (json.equals("0")) {
                            dialog.dismiss();
                            empty.show(false, "", "网络异常", "重试", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getData(1);
                                }
                            });
                            return;
                        } else if (json.equals("1")) {
                            dialog.dismiss();
                            empty.show(false, "", "网络超时", "重试", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getData(1);
                                }
                            });
                            return;
                        }
                        Gson gson = new Gson();
                        final List<MyKTBean> kclist = gson.fromJson(json, new TypeToken<List<MyKTBean>>() {
                        }.getType());
                        MyKTAdapter adapter = new MyKTAdapter(getContext(), kclist);
                        if (kclist != null && kclist.size() > 0) {

                            gv.setNumColumns(1);
                            gv.setAdapter(adapter);

                            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Intent intent = new Intent();
                                    intent.setClass(getContext(), TGDetailActivity.class);
                                    intent.putExtra("kt", 1);
                                    intent.putExtra("tuanid", kclist.get(position).getTuanid());
                                    intent.putExtra("type", 1);
                                    getContext().startActivity(intent);

                                }
                            });
swip.setVisibility(View.VISIBLE);
                        }else {
                            empty.show("", "暂无团购");
                        }

                        dialog.dismiss();
                    } catch (Exception e) {
                        dialog.dismiss();
                        empty.show(false, "", "数据异常", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData(1);
                            }
                        });
                    }

                }
            });

        } else if (type == 2) {
            OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).MYCT(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

                @Override
                public void onsendJson(String json) {
                    try {
                        if (json.equals("0")) {
                            dialog.dismiss();
                            empty.show(false, "", "网络异常", "重试", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getData(2);
                                }
                            });
                            return;
                        } else if (json.equals("1")) {
                            dialog.dismiss();
                            empty.show(false, "", "网络超时", "重试", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getData(2);
                                }
                            });
                            return;
                        }
                        Gson gson = new Gson();
                        final List<MyCTBean> ctlist = gson.fromJson(json, new TypeToken<List<MyCTBean>>() {
                        }.getType());
                        if (ctlist != null && ctlist.size() > 0) {
                            gv.setNumColumns(1);
                            gv.setAdapter(new MyCTAdapter(getContext(), ctlist));
                            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent();
                                    intent.setClass(getContext(), TGDetailActivity.class);
                                    intent.putExtra("ct", 1);
                                    intent.putExtra("tuanid", ctlist.get(position).getTuanid());
                                    intent.putExtra("type", 2);
                                    getContext().startActivity(intent);
                                }
                            });
                            swip.setVisibility(View.VISIBLE);
                        }else {
                            empty.show("", "暂无团购");
                        }
                        dialog.dismiss();
                    } catch (Exception e) {
//                        Toast.makeText(getContext(), "数据异常", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        empty.show(false, "", "数据异常", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData(1);
                            }
                        });
                    }
                }
            });
        }
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                getData(type);
                swip.setRefreshing(false);
            }
        }, 3000);
    }
}




