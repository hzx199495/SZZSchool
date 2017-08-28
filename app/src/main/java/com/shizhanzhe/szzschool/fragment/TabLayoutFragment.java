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
import com.shizhanzhe.szzschool.activity.LoginActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.SZActivity;
import com.shizhanzhe.szzschool.adapter.Videoadapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.PolyvPlayerActivity;

import java.util.ArrayList;

/**
 * Created by zz9527 on 2017/5/3.
 */

public class TabLayoutFragment extends Fragment {
    public static String TABLAYOUT_FRAGMENT = "tab_fragment";
    private int type;
    private ListView lv;


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
        SharedPreferences preferences =getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
         vip = preferences.getString("vip", "");
        lv = (ListView) view.findViewById(R.id.lv);
        getData();

    }

    ProDeatailBean.CiBean list;

    protected void initView() {


        switch (type) {
            case 1:

                final ArrayList<VideoBean> videolist1 = new ArrayList<>();
                for (ProDeatailBean.CiBean.A0Bean.ChoiceKcBean bean : list.getA0().getChoice_kc()
                        ) {
                    VideoBean video = new VideoBean();
                    video.setGrade(bean.getGrade());
                    video.setId(bean.getId());
                    video.setKc_hours(bean.getKc_hours());
                    video.setMv_url(bean.getMv_url());
                    video.setName(bean.getName());
                    video.setSort(bean.getSort());
                    videolist1.add(video);
                }
                lv.setAdapter(new Videoadapter(getContext(), videolist1, txId));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        if (vip.equals("1") || isbuy.equals("1")) {
                            if (videolist1.get(position).getGrade().contains("2") || videolist1.get(position).getGrade().contains("1")) {
                                MyApplication.position=position;
                                MyApplication.videotypeid=list.getA0().getId();
                                MyApplication.videotype = type;
                                MyApplication.videoitemid = videolist1.get(position).getId();
                                Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, videolist1.get(position).getMv_url());
                                getContext().startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("实战者学院提示");
                                builder.setMessage("不建议越级观看，确认继续");
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        MyApplication.position=position;
                                        MyApplication.videotypeid=list.getA0().getId();
                                        MyApplication.videotype = type;
                                        MyApplication.videoitemid = videolist1.get(position).getId();
                                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, videolist1.get(position).getMv_url());
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

                    }
                });
                break;
            case 2:

                final ArrayList<VideoBean> videolist2 = new ArrayList<>();
                for (ProDeatailBean.CiBean.A1Bean.ChoiceKcBeanX bean : list.getA1().getChoice_kc()
                        ) {
                    VideoBean video = new VideoBean();
                    video.setGrade(bean.getGrade());
                    video.setId(bean.getId());
                    video.setKc_hours(bean.getKc_hours());
                    video.setMv_url(bean.getMv_url());
                    video.setName(bean.getName());
                    video.setSort(bean.getSort());
                    videolist2.add(video);
                }
                lv.setAdapter(new Videoadapter(getContext(), videolist2, txId));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        if (vip.equals("1") || isbuy.equals("1")) {
                            if (videolist2.get(position).getGrade().contains("2") || videolist2.get(position).getGrade().contains("1")) {
                                MyApplication.position=position;
                                MyApplication.videotypeid=list.getA1().getId();
                                MyApplication.videotype = type;
                                MyApplication.videoitemid = videolist2.get(position).getId();
                                Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, videolist2.get(position).getMv_url());
                                getContext().startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("实战者学院提示");
                                builder.setMessage("不建议越级观看，确认继续");
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        MyApplication.position=position;
                                        MyApplication.videotypeid=list.getA1().getId();
                                        MyApplication.videotype = type;
                                        MyApplication.videoitemid = videolist2.get(position).getId();
                                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, videolist2.get(position).getMv_url());
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
                    }
                });
                break;
            case 3:
                final ArrayList<VideoBean> videolist3 = new ArrayList<>();
                for (ProDeatailBean.CiBean.A2Bean.ChoiceKcBeanXX bean : list.getA2().getChoice_kc()
                        ) {
                    VideoBean video = new VideoBean();
                    video.setGrade(bean.getGrade());
                    video.setId(bean.getId());
                    video.setKc_hours(bean.getKc_hours());
                    video.setMv_url(bean.getMv_url());
                    video.setName(bean.getName());
                    video.setSort(bean.getSort());
                    videolist3.add(video);
                }
                lv.setAdapter(new Videoadapter(getContext(), videolist3, txId));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        if (vip.equals("1") || isbuy.equals("1")) {
                            if (videolist3.get(position).getGrade().contains("2") || videolist3.get(position).getGrade().contains("1")) {
                                MyApplication.position=position;
                                MyApplication.videotypeid=list.getA2().getId();
                                MyApplication.videotype = type;
                                MyApplication.videoitemid = videolist3.get(position).getId();
                                Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, videolist3.get(position).getMv_url());
                                getContext().startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("实战者学院提示");
                                builder.setMessage("不建议越级观看，确认继续");
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        MyApplication.position=position;
                                        MyApplication.videotypeid=list.getA2().getId();
                                        MyApplication.videotype = type;
                                        MyApplication.videoitemid = videolist3.get(position).getId();
                                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, videolist3.get(position).getMv_url());
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
                    }
                });
                break;
            case 4:
                final ArrayList<VideoBean> videolist4 = new ArrayList<>();
                for (ProDeatailBean.CiBean.A3Bean.ChoiceKcBeanXXX bean : list.getA3().getChoice_kc()
                        ) {
                    VideoBean video = new VideoBean();
                    video.setGrade(bean.getGrade());
                    video.setId(bean.getId());
                    video.setKc_hours(bean.getKc_hours());
                    video.setMv_url(bean.getMv_url());
                    video.setName(bean.getName());
                    video.setSort(bean.getSort());
                    videolist4.add(video);
                }
                lv.setAdapter(new Videoadapter(getContext(), videolist4, txId));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        if (vip.equals("1") || isbuy.equals("1")) {
                            if (videolist4.get(position).getGrade().contains("2") || videolist4.get(position).getGrade().contains("1")) {
                                MyApplication.position=position;
                                MyApplication.videotypeid=list.getA3().getId();
                                MyApplication.videotype = type;
                                MyApplication.videoitemid = videolist4.get(position).getId();
                                Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, videolist4.get(position).getMv_url());
                                getContext().startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("实战者学院提示");
                                builder.setMessage("不建议越级观看，确认继续");
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        MyApplication.position=position;
                                        MyApplication.videotypeid=list.getA3().getId();
                                        MyApplication.videotype = type;
                                        MyApplication.videoitemid = videolist4.get(position).getId();
                                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, videolist4.get(position).getMv_url());
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
                    }
                });
                break;
            case 5:
                final ArrayList<VideoBean> videolist5 = new ArrayList<>();
                for (ProDeatailBean.CiBean.A4Bean.ChoiceKcBeanXXXX bean : list.getA4().getChoice_kc()
                        ) {
                    VideoBean video = new VideoBean();
                    video.setGrade(bean.getGrade());
                    video.setId(bean.getId());
                    video.setKc_hours(bean.getKc_hours());
                    video.setMv_url(bean.getMv_url());
                    video.setName(bean.getName());
                    video.setSort(bean.getSort());
                    videolist5.add(video);
                }
                lv.setAdapter(new Videoadapter(getContext(), videolist5, txId));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (vip.equals("1") || isbuy.equals("1")) {
                            MyApplication.position=position;
                            MyApplication.videotypeid=list.getA4().getId();

//                            if (videolist5.get(position).getGrade().contains("2") || videolist5.get(position).getGrade().contains("1")) {
                                MyApplication.videotype = type;
                                MyApplication.videoitemid = videolist5.get(position).getId();
                                Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, videolist5.get(position).getMv_url());
                                getContext().startActivity(intent);
//                            } else {

//                                Toast.makeText(getActivity(), "无法越级学习！", Toast.LENGTH_SHORT).show();
//                            }
                        } else {
                            Toast.makeText(getActivity(), "未购买课程无法学习", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }


    }

    String txId;
    String isbuy;

    void getData() {
        Gson gson = new Gson();
        list = gson.fromJson(MyApplication.videojson, ProDeatailBean.class).getCi();
        ProDeatailBean.TxBean tx = gson.fromJson(MyApplication.videojson, ProDeatailBean.class).getTx();
        txId = tx.getId();
        isbuy = tx.getIsbuy();
        MyApplication.videosuggest=tx.getSys_hours();
        MyApplication.txId=txId;
        MyApplication.videotitle=tx.getStitle();
        initView();

    }

    @Override
    public void onResume() {
        super.onResume();
        OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).SECOND(MyApplication.txId), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                if (!json.equals(MyApplication.videojson)){
                    MyApplication.videojson = json;
                    getData();
                }
            }
        });
    }
}
