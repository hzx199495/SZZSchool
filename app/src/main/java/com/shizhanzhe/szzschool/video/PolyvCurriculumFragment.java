package com.shizhanzhe.szzschool.video;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.easefun.polyvsdk.PolyvBitRate;
import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.Bean.VideoBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.adapter.Videoadapter;

import java.util.ArrayList;

public class PolyvCurriculumFragment extends Fragment {
    // viewpager切换的时候，fragment执行销毁View方法，但fragment对象没有被销毁
    // 课程目录的listView
    private ListView lv_cur;
    // fragmentView
    private View view;
    // 加载中控件
    private ProgressBar pb_loading;

    private PolyvPermission polyvPermission = null;
    private String videoId = "";
    private static final int SETTING = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (view == null)
            view = inflater.inflate(R.layout.polyv_fragment_tab_cur, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findIdAndNew();
        Gson gson = new Gson();
        final ProDeatailBean.CiBean cibean= gson.fromJson(MyApplication.videojson, ProDeatailBean.class).getCi();
        final ArrayList<VideoBean> videolist = new ArrayList<>();
        if (MyApplication.videotype==1){
            for (ProDeatailBean.CiBean.A0Bean.ChoiceKcBean bean: cibean.getA0().getChoice_kc()
                    ) {
                VideoBean video = new VideoBean();
                video.setGrade(bean.getGrade());
                video.setId(bean.getId());
                video.setKc_hours(bean.getKc_hours());
                video.setMv_url(bean.getMv_url());
                video.setName(bean.getName());
                video.setSort(bean.getSort());
                videolist.add(video);
            }
        }else if (MyApplication.videotype==2){
            for (ProDeatailBean.CiBean.A1Bean.ChoiceKcBeanX bean: cibean.getA1().getChoice_kc()
                    ) {
                VideoBean video = new VideoBean();
                video.setGrade(bean.getGrade());
                video.setId(bean.getId());
                video.setKc_hours(bean.getKc_hours());
                video.setMv_url(bean.getMv_url());
                video.setName(bean.getName());
                video.setSort(bean.getSort());
                videolist.add(video);
            }
        }else if (MyApplication.videotype==3){
            for (ProDeatailBean.CiBean.A2Bean.ChoiceKcBeanXX bean: cibean.getA2().getChoice_kc()
                    ) {
                VideoBean video = new VideoBean();
                video.setGrade(bean.getGrade());
                video.setId(bean.getId());
                video.setKc_hours(bean.getKc_hours());
                video.setMv_url(bean.getMv_url());
                video.setName(bean.getName());
                video.setSort(bean.getSort());
                videolist.add(video);
            }
        }else if (MyApplication.videotype==4){
            for (ProDeatailBean.CiBean.A3Bean.ChoiceKcBeanXXX bean: cibean.getA3().getChoice_kc()
                    ) {
                VideoBean video = new VideoBean();
                video.setGrade(bean.getGrade());
                video.setId(bean.getId());
                video.setKc_hours(bean.getKc_hours());
                video.setMv_url(bean.getMv_url());
                video.setName(bean.getName());
                video.setSort(bean.getSort());
                videolist.add(video);
            }
        }else if (MyApplication.videotype==5){
            for (ProDeatailBean.CiBean.A4Bean.ChoiceKcBeanXXXX bean: cibean.getA4().getChoice_kc()
                    ) {
                VideoBean video = new VideoBean();
                video.setGrade(bean.getGrade());
                video.setId(bean.getId());
                video.setKc_hours(bean.getKc_hours());
                video.setMv_url(bean.getMv_url());
                video.setName(bean.getName());
                video.setSort(bean.getSort());
                videolist.add(video);
            }
        }
        final Videoadapter videoadapter = new Videoadapter(getContext(), videolist, "0");
        lv_cur.setAdapter(videoadapter);
        videoadapter.setSelectItem(MyApplication.position);
        videoadapter.notifyDataSetInvalidated();
        lv_cur.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                videoadapter.setSelectItem(position);
                videoadapter.notifyDataSetInvalidated();
                if (videolist.get(position).getGrade().contains("2") || videolist.get(position).getGrade().contains("1")) {
                    MyApplication.videoitemid=videolist.get(position).getId();
                    videoId = videolist.get(position).getMv_url();
                    polyvPermission.applyPermission(getActivity(), PolyvPermission.OperationType.play);
                    polyvPermission.setResponseCallback(new PolyvPermission.ResponseCallback() {
                        @Override
                        public void callback() {
                            requestPermissionWriteSettings();
                        }
                    });
                }else {
                    Toast.makeText(getActivity(), "无法越级学习！", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void findIdAndNew() {
        lv_cur = (ListView) view.findViewById(R.id.lv_cur);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null)
            ((ViewGroup) view.getParent()).removeView(view);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            polyvPermission = new PolyvPermission();

        }



    /**
     * 请求写入设置的权限
     */
    @SuppressLint("InlinedApi")
    private void requestPermissionWriteSettings() {
        if (!PolyvPermission.canMakeSmores()) {
            ((PolyvPlayerActivity) getActivity()).play(videoId, PolyvBitRate.ziDong.getNum(), true, false);
        } else if (Settings.System.canWrite(this.getActivity())) {
            ((PolyvPlayerActivity) getActivity()).play(videoId, PolyvBitRate.ziDong.getNum(), true, false);
        } else {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + this.getActivity().getPackageName()));
            startActivityForResult(intent, SETTING);
        }
    }

    @Override
    @SuppressLint("InlinedApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTING) {
            if (Settings.System.canWrite(this.getActivity())) {
                ((PolyvPlayerActivity) getActivity()).play(videoId, PolyvBitRate.ziDong.getNum(), false, false);
            } else {
                new AlertDialog.Builder(this.getActivity())
                        .setTitle("showPermissionInternet")
                        .setMessage(Settings.ACTION_MANAGE_WRITE_SETTINGS + " not granted")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
        }
    }

    /**
     * This is the method that is hit after the user accepts/declines the
     * permission you requested. For the purpose of this example I am showing a "success" header
     * when the user accepts the permission and a snackbar when the user declines it.  In your application
     * you will want to handle the accept/decline in a way that makes sense.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (polyvPermission.operationHasPermission(requestCode)) {
            requestPermissionWriteSettings();
        } else {
            polyvPermission.makePostRequestSnack();
        }
    }

}
