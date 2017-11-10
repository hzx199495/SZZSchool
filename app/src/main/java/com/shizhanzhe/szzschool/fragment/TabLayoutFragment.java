package com.shizhanzhe.szzschool.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.LoginActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
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
    private String  vjson;
    private ListView lv;
    private StateLayout state_layout;
    public static TabLayoutFragment newInstance(int type,String json) {
        TabLayoutFragment fragment = new TabLayoutFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLAYOUT_FRAGMENT, type);
        bundle.putString("json",json);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            type = (int) getArguments().getSerializable(TABLAYOUT_FRAGMENT);
            vjson=getArguments().getString("json");
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
        state_layout= (StateLayout) view.findViewById(R.id.state_layout);
        state_layout.setTipText(StateLayout.EMPTY," ");
        state_layout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                state_layout.showLoadingView();
                getData();
            }

            @Override
            public void loginClick() {

            }
        });
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
                    lv.setAdapter(new Videoadapter(getContext(), fourlist, txId, protype));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            if (MyApplication.isLogin) {
                                if (vip.equals("1") || isbuy.equals("1")) {

                                    MyApplication.position = position;
                                    MyApplication.videotypeid = fourlist.get(position).getId();
                                    MyApplication.videotype = type;
                                    MyApplication.videoitemid = fourlist.get(position).getId();
                                    Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, fourlist.get(position).getMv_url(),vjson);
                                    getContext().startActivity(intent);


                                } else {
                                    if (fourlist.size() > 11 && position <= 11) {
                                        MyApplication.position = position;
                                        MyApplication.videotypeid = fourlist.get(position).getId();
                                        MyApplication.videotype = type;
                                        MyApplication.videoitemid = fourlist.get(position).getId();
                                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, fourlist.get(position).getMv_url(),vjson);
                                        getContext().startActivity(intent);
                                    } else if (fourlist.size() < 11 && position <= 6) {
                                        MyApplication.position = position;
                                        MyApplication.videotypeid = fourlist.get(position).getId();
                                        MyApplication.videotype = type;
                                        MyApplication.videoitemid = fourlist.get(position).getId();
                                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, fourlist.get(position).getMv_url(),vjson);
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
                    setData2(4);
                }
                break;
            case 3:
                setData(2);
                break;
            case 4:
                setData(3);
                break;
            case 5:
                setData2(4);
                break;
            case 6:
                setData2(5);
                break;
        }
    }

    void setData2(int position) {
        try {
            final List<ProDeatailBean.CiBean.ChoiceKcBean> choice_kc = list.get(position).getChoice_kc();
            lv.setAdapter(new Videoadapter(getContext(), choice_kc, txId, protype));
            if (choice_kc.size()==0){
                state_layout.showEmptyView();
            }
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    if (MyApplication.isLogin) {
                        if (vip.equals("1") || isbuy.equals("1")) {
                            MyApplication.position = position;
                            MyApplication.videotypeid = choice_kc.get(position).getId();
                            MyApplication.videotype = type;
                            MyApplication.videoitemid = choice_kc.get(position).getId();
                            Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, choice_kc.get(position).getMv_url(),vjson);
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
        lv.setAdapter(new Videoadapter(getContext(), choice_kc, txId, protype));
        if (choice_kc.size()==0){
            state_layout.showEmptyView();
        }
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
                            Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, choice_kc.get(position).getMv_url(),vjson);
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
                                    Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, choice_kc.get(position).getMv_url(),vjson);
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
            list = gson.fromJson(vjson, ProDeatailBean.class).getCi();
            ProDeatailBean.TxBean tx = gson.fromJson(vjson, ProDeatailBean.class).getTx();
            txId = tx.getId();
            isbuy = tx.getIsbuy();
            if (tx.getCatid().contains("41")) {
                protype = 1;
            } else {
                protype = 2;
            }
            initView();
        } catch (Exception e) {

        }


    }
    boolean flag=false;
    @Override
    public void onPause() {
        super.onPause();
        flag=true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (flag) {
            OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).SECOND(txId), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {
                    if (!json.equals(vjson)) {
                        vjson = json;
                        getData();
                    }
                }
            });
        }
    }
}
