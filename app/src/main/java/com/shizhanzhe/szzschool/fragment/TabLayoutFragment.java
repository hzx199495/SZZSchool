package com.shizhanzhe.szzschool.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.LoginActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.adapter.Videoadapter;
import com.shizhanzhe.szzschool.utils.Data;
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
    private String vjson;
    private ListView lv;
    private QMUIEmptyView empty;
    private QMUITipDialog dialog;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    dialog.dismiss();
                    break;
            }
        }
    };

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
            vjson = Data.getData();
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
        ImageView iv = view.findViewById(R.id.iv);
        lv.setEmptyView(iv);
        empty = view.findViewById(R.id.empty);
        getData();

    }

    List<ProDeatailBean.CiBean> list;

    protected void initView() {

        switch (type) {
            case 1:
                if (protype == 1) {
                    setData(0);
                } else if (protype == 2) {
                    final List<ProDeatailBean.CiBean.ChoiceKcBean> fourlist = list.get(0).getChoice_kc();
//                    for (int i = 0; i < 4; i++) {
//                        try {
//                            for (ProDeatailBean.CiBean.ChoiceKcBean b : list.get(i).getChoice_kc()) {
//                                fourlist.add(b);
//                            }
//                        } catch (Exception e) {
//
//                        }
//
//                    }
                    lv.setAdapter(new Videoadapter(getContext(), fourlist, txId, protype));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            if (MyApplication.isLogin) {
                                Data.setData(vjson);
                                if (vip.equals("1") || isbuy.equals("1")) {

                                    MyApplication.position = position;
                                    MyApplication.videotypeid = list.get(0).getId();
                                    MyApplication.videotype = type;
                                    MyApplication.videoitemid = fourlist.get(position).getId();
                                    MyApplication.videoname = fourlist.get(position).getName();
                                    Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, fourlist.get(position).getMv_url());
                                    getContext().startActivity(intent);


                                } else {
                                    if (fourlist.size() > 11 && position <= 11) {
                                        MyApplication.position = position;
                                        MyApplication.videotypeid = list.get(0).getId();
                                        MyApplication.videotype = type;
                                        MyApplication.videoname = fourlist.get(position).getName();
                                        MyApplication.videoitemid = fourlist.get(position).getId();
                                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, fourlist.get(position).getMv_url());
                                        getContext().startActivity(intent);
                                    } else if (fourlist.size() < 11 && position <= 6) {
                                        MyApplication.position = position;
                                        MyApplication.videotypeid = list.get(0).getId();
                                        MyApplication.videotype = type;
                                        MyApplication.videoitemid = fourlist.get(position).getId();
                                        MyApplication.videoname = fourlist.get(position).getName();
                                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, fourlist.get(position).getMv_url());
                                        getContext().startActivity(intent);
                                    } else {
                                        dialog = new QMUITipDialog.Builder(getContext()).setIconType(4).setTipWord("未购买课程无法学习").create();
                                        dialog.show();
                                        mhandler.sendEmptyMessageDelayed(1, 1500);
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
                    setData(4);
                }
                break;
            case 3:
                setData(2);
                break;
            case 4:
                setData(3);
                break;
            case 5:
                setData(4);
                break;
            case 6:
                setData(5);
                break;
            case 7:
                setData(6);
                break;
            case 8:
                setData(7);
                break;
        }
    }

//    void setData2(final int tabposition) {
//        try {
//            final List<ProDeatailBean.CiBean.ChoiceKcBean> choice_kc = list.get(tabposition).getChoice_kc();
//            lv.setAdapter(new Videoadapter(getContext(), choice_kc, txId, protype,mian));
//
//            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                    if (MyApplication.isLogin) {
//                        if (vip.equals("1") || isbuy.equals("1")) {
//                            MyApplication.position = position;
//                            MyApplication.videotypeid = list.get(tabposition).getId();
//                            MyApplication.videotype = type;
//                            MyApplication.videoitemid = choice_kc.get(position).getId();
//                            MyApplication.videoname = choice_kc.get(position).getName();
//                            Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, choice_kc.get(position).getMv_url(), vjson);
//                            getContext().startActivity(intent);
//                        } else {
//
//                            dialog = new QMUITipDialog.Builder(getContext()).setIconType(4).setTipWord("未购买课程无法学习").create();
//                            dialog.show();
//                            mhandler.sendEmptyMessageDelayed(1, 1500);
//                        }
//                    } else {
//                        Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getContext(), LoginActivity.class));
//                    }
//
//                }
//            });
//        } catch (Exception e) {
//            return;
//        }
//    }

    void setData(final int tabposition) {
        final List<ProDeatailBean.CiBean.ChoiceKcBean> choice_kc = list.get(tabposition).getChoice_kc();
        lv.setAdapter(new Videoadapter(getContext(), choice_kc, txId, protype,mian));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.i("_______",mian.contains(choice_kc.get(position).getName())+"_"+mian+"_"+choice_kc.get(position).getName());
                if (MyApplication.isLogin) {
                    Data.setData(vjson);
                    if (vip.equals("1") || isbuy.equals("1")) {
                        if (choice_kc.get(position).getGrade().contains("2") || choice_kc.get(position).getGrade().contains("1")) {
                            MyApplication.position = position;
                            MyApplication.videotypeid = list.get(tabposition).getId();
                            MyApplication.videotype = type;
                            MyApplication.videoitemid = choice_kc.get(position).getId();
                            Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, choice_kc.get(position).getMv_url());
                            getContext().startActivity(intent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("实战者教育学院提示");
                            builder.setMessage("您确定要越级学习吗");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MyApplication.position = position;
                                    MyApplication.videotypeid = list.get(tabposition).getId();
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
                    } else if (mian.contains(choice_kc.get(position).getId())) {
                        MyApplication.position = position;
                        MyApplication.videotypeid = list.get(tabposition).getId();
                        MyApplication.videotype = type;
                        MyApplication.videoitemid = choice_kc.get(position).getId();
                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, choice_kc.get(position).getMv_url());
                        getContext().startActivity(intent);
                    } else {
                        dialog = new QMUITipDialog.Builder(getContext()).setIconType(4).setTipWord("未购买课程无法学习").create();
                        dialog.show();
                        mhandler.sendEmptyMessageDelayed(1, 1500);
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
    String mian="";

    void getData() {
        try {
            Gson gson = new Gson();
            ProDeatailBean bean = gson.fromJson(vjson, ProDeatailBean.class);
            list = bean.getCi();
            ProDeatailBean.TxBean tx = bean.getTx();
            txId = tx.getId();
            isbuy = tx.getIsbuy();
            if (tx.getCatid().contains("41")) {
                mian = tx.getMian();
                protype = 1;//网络课程
            } else {
                protype = 2;//职业课程
            }
            initView();
        } catch (Exception e) {

        }


    }

    boolean flag = false;

    @Override
    public void onPause() {
        super.onPause();
        flag = true;
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
