package com.shizhanzhe.szzschool.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.Bean.VideoBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.LoginActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.SZActivity;
import com.shizhanzhe.szzschool.adapter.Videoadapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.PolyvPlayerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2017/5/3.
 */

public class TabLayoutFragment extends Fragment {
    public static String TABLAYOUT_FRAGMENT = "tab_fragment";
    private int type;
    private ListView lv;
    private View nodata;

    public static TabLayoutFragment newInstance(int type) {
        TabLayoutFragment fragment = new TabLayoutFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLAYOUT_FRAGMENT, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            type = (int) getArguments().getSerializable(TABLAYOUT_FRAGMENT);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablayout, container, false);

        return view;
    }

    String vip;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
        vip = preferences.getString("vip", "");
        lv = (ListView) view.findViewById(R.id.lv);
        nodata = view.findViewById(R.id.nodata);
        getData();

    }

    List<ProDeatailBean.CiBean> list;

    protected void initView() {

        switch (type) {
            case 1:
                if (protype == 1) {
                    setData(0);
                } else if (protype == 2) {
                    final List<ProDeatailBean.CiBean.ChoiceKcBean> fourlist = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        try {
                            for (ProDeatailBean.CiBean.ChoiceKcBean b : list.get(i).getChoice_kc()) {
                                fourlist.add(b);
                            }
                        } catch (Exception e) {

                        }

                    }
                    lv.setAdapter(new Videoadapter(getContext(), fourlist, txId,protype));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            if (MyApplication.isLogin) {
                                if (vip.equals("1") || isbuy.equals("1")) {
                                    MyApplication.position = position;
                                    MyApplication.videotypeid = fourlist.get(position).getId();
                                    MyApplication.videotype = type;
                                    MyApplication.videoitemid = fourlist.get(position).getId();
                                    Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, fourlist.get(position).getMv_url());
                                    getContext().startActivity(intent);
                                } else {
                                    if (fourlist.size() > 11 && position <= 11) {
                                        MyApplication.position = position;
                                        MyApplication.videotypeid = fourlist.get(position).getId();
                                        MyApplication.videotype = type;
                                        MyApplication.videoitemid = fourlist.get(position).getId();
                                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, fourlist.get(position).getMv_url());
                                        getContext().startActivity(intent);
                                    } else if (fourlist.size() < 11 && position <= 6) {
                                        MyApplication.position = position;
                                        MyApplication.videotypeid = fourlist.get(position).getId();
                                        MyApplication.videotype = type;
                                        MyApplication.videoitemid = fourlist.get(position).getId();
                                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, fourlist.get(position).getMv_url());
                                        getContext().startActivity(intent);
                                    } else {
                                        new SVProgressHUD(getActivity()).showErrorWithStatus("未购买课程无法学习", SVProgressHUD.SVProgressHUDMaskType.None);
                                    }
                                }
                            } else {
                                Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getContext(), LoginActivity.class));
                            }
                        }
                    });
                }
                break;
            case 2:
                if (protype == 1) {
                    setData(1);
                } else if (protype == 2) {
                    setData2();
                }
                break;
            case 3:
                setData(2);
                break;
            case 4:
                setData(3);
                break;
            case 5:
                setData2();
                break;
        }


    }

    void setData2() {
        try {
            final List<ProDeatailBean.CiBean.ChoiceKcBean> choice_kc = list.get(4).getChoice_kc();
            lv.setEmptyView(nodata);
            lv.setAdapter(new Videoadapter(getContext(), choice_kc, txId,protype));
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    if (MyApplication.isLogin) {
                        if (vip.equals("1") || isbuy.equals("1")) {
                            MyApplication.position = position;
                            MyApplication.videotypeid = choice_kc.get(position).getId();
                            MyApplication.videotype = type;
                            MyApplication.videoitemid = choice_kc.get(position).getId();
                            Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, choice_kc.get(position).getMv_url());
                            getContext().startActivity(intent);
                        } else {
                            new SVProgressHUD(getActivity()).showErrorWithStatus("未购买课程无法学习", SVProgressHUD.SVProgressHUDMaskType.None);
                        }
                    } else {
                        Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }

                }
            });
        } catch (Exception e) {
            return;
        }
    }

    void setData(int tabposition) {
        final List<ProDeatailBean.CiBean.ChoiceKcBean> choice_kc = list.get(tabposition).getChoice_kc();
        lv.setEmptyView(nodata);
        lv.setAdapter(new Videoadapter(getContext(), choice_kc, txId,protype));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (MyApplication.isLogin) {
                    if (vip.equals("1") || isbuy.equals("1")) {
                        if (choice_kc.get(position).getGrade().contains("2") || choice_kc.get(position).getGrade().contains("1")) {
                            MyApplication.position = position;
                            MyApplication.videotypeid = choice_kc.get(position).getId();
                            MyApplication.videotype = type;
                            MyApplication.videoitemid = choice_kc.get(position).getId();
                            Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, choice_kc.get(position).getMv_url());
                            getContext().startActivity(intent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("实战者学院提示");
                            builder.setMessage("不建议越级观看，确认继续");
                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MyApplication.position = position;
                                    MyApplication.videotypeid = choice_kc.get(position).getId();
                                    MyApplication.videotype = type;
                                    MyApplication.videoitemid = choice_kc.get(position).getId();
                                    Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, choice_kc.get(position).getMv_url());
                                    getContext().startActivity(intent);
                                }
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }

                            });
                            builder.create().show();
                        }
                    } else {
                        new SVProgressHUD(getActivity()).showErrorWithStatus("未购买课程无法学习", SVProgressHUD.SVProgressHUDMaskType.None);
                    }
                } else {
                    Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }

            }
        });
    }


    String txId;
    String isbuy;
    int protype;

    void getData() {
        try {
            Gson gson = new Gson();
            list = gson.fromJson(MyApplication.videojson, ProDeatailBean.class).getCi();
            ProDeatailBean.TxBean tx = gson.fromJson(MyApplication.videojson, ProDeatailBean.class).getTx();
            txId = tx.getId();
            isbuy = tx.getIsbuy();
            if (tx.getCatid().contains("41")) {
                protype = 1;
            } else {
                protype = 2;
            }
            MyApplication.videosuggest = tx.getSys_hours();
            MyApplication.txId = txId;
            MyApplication.videotitle = tx.getStitle();
            MyApplication.videototal = tx.getKeshi();
            initView();
        }catch (Exception e){

        }


    }

    @Override
    public void onResume() {
        super.onResume();
        OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).SECOND(MyApplication.txId), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                if (!json.equals(MyApplication.videojson)) {
                    MyApplication.videojson = json;
                    getData();
                }
            }
        });
    }
}
