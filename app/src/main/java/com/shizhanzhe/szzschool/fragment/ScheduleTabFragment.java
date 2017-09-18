package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.Bean.ScheduleBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.adapter.ScheduleDeatilAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.PolyvPlayerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2017/8/17.
 */

public class ScheduleTabFragment extends Fragment {

    public static String TABLAYOUT_FRAGMENT = "tab_fragment";
    private int type;
    ListView lv;
    private SVProgressHUD svProgressHUD;
    private View nodata;

    public static ScheduleTabFragment newInstance(int type, String id) {
        ScheduleTabFragment fragment = new ScheduleTabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLAYOUT_FRAGMENT, type);
        bundle.putString("id", id);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = (ListView) view.findViewById(R.id.lv);
        nodata = view.findViewById(R.id.nodata);
        lv.setEmptyView(nodata);
        getdata(getArguments().getString("id"));
    }

    private void initView(final List<ScheduleBean.InfoBean.KcDataBean> list) {

        switch (type) {
            case 1:
                try {
                    if (protype == 1) {
                        setData(list, 0);
                    } else if (protype == 2) {
                        final List<ScheduleBean.InfoBean.KcDataBean.VdataBean> fourlist = new ArrayList<>();
                        try {
                            for (int i = 0; i < 4; i++) {
                                for (ScheduleBean.InfoBean.KcDataBean.VdataBean b : list.get(i).getVdata()) {
                                    fourlist.add(b);
                                }
                            }
                        } catch (Exception e) {
                        }
                        lv.setAdapter(new ScheduleDeatilAdapter(getContext(), fourlist));
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                if (list.get(0).getVdata().get(position).getVdetail().getGuantime() != null) {
                                    MyApplication.schedule = Integer.parseInt(fourlist.get(position).getVdetail().getGuantime());
                                } else {
                                    MyApplication.schedule = 0;
                                }
                                MyApplication.position = position;
                                MyApplication.videotypeid = list.get(position).getCid();
                                MyApplication.videotype = type;
                                MyApplication.videoitemid = fourlist.get(position).getVid();
                                Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, prodetaillist.get(position).getChoice_kc().get(position).getMv_url());
                                getContext().startActivity(intent);
                            }
                        });
                    }
                } catch (Exception e) {
                }
                break;
            case 2:
                if (protype == 1) {
                    setData(list, 1);
                } else {
                    setData(list, 4);
                }

                break;
            case 3:
                setData(list, 2);
                break;
            case 4:

                setData(list, 3);
                break;
            case 5:
                setData(list, 4);
                break;
        }
    }

    void setData(final List<ScheduleBean.InfoBean.KcDataBean> list, final int tabposition) {
        try {
            final List<ScheduleBean.InfoBean.KcDataBean.VdataBean> vdata = list.get(tabposition).getVdata();
            lv.setAdapter(new ScheduleDeatilAdapter(getContext(), vdata));
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    if (vdata.get(position).getVdetail().getGuantime() != null) {
                        MyApplication.schedule = Integer.parseInt(vdata.get(position).getVdetail().getGuantime());
                    } else {
                        MyApplication.schedule = 0;
                    }
                    MyApplication.position = position;
                    MyApplication.videotypeid = list.get(tabposition).getCid();
                    MyApplication.videotype = type;
                    MyApplication.videoitemid = vdata.get(position).getVid();
                    Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, prodetaillist.get(tabposition).getChoice_kc().get(position).getMv_url());
                    getContext().startActivity(intent);
                }
            });
        } catch (Exception e) {
            return;
        }
    }

    List<ScheduleBean.InfoBean.KcDataBean> list;
    List<ProDeatailBean.CiBean> prodetaillist;
    int protype;

    void getdata(String id) {
        Gson gson = new Gson();
        prodetaillist = gson.fromJson(MyApplication.videojson, ProDeatailBean.class).getCi();
        ProDeatailBean.TxBean tx = gson.fromJson(MyApplication.videojson, ProDeatailBean.class).getTx();
        MyApplication.txId = tx.getId();
        MyApplication.videotitle = tx.getStitle();
        if (tx.getCatid().contains("41")) {
            protype = 1;
        } else {
            protype = 2;
        }
        OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).STUDYDETAIL(id), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                String json2 = json.replace("\"vdetail\":[]", "\"vdetail\":{}");
                list = gson.fromJson(json2, ScheduleBean.class).getInfo().getKc_data();
                initView(list);
            }
        });

    }
}
