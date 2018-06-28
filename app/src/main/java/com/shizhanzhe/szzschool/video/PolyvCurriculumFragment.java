package com.shizhanzhe.szzschool.video;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.SZActivity;
import com.shizhanzhe.szzschool.adapter.Videoadapter;

import java.util.List;

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
    public static PolyvCurriculumFragment newInstance(String vjson) {

        Bundle args = new Bundle();
        args.putString("json",vjson);
        PolyvCurriculumFragment fragment = new PolyvCurriculumFragment();
        fragment.setArguments(args);
        return fragment;
    }
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
        try {


        Gson gson = new Gson();
        final List<ProDeatailBean.CiBean> cibean = gson.fromJson(getArguments().getString("json"), ProDeatailBean.class).getCi();
        final ProDeatailBean.CiBean bean = cibean.get(MyApplication.videotype - 1);
        final Videoadapter videoadapter = new Videoadapter(getContext(), bean.getChoice_kc(), "0", 0);
        lv_cur.setAdapter(videoadapter);
        videoadapter.setSelectItem(MyApplication.position);
        videoadapter.notifyDataSetInvalidated();

        lv_cur.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyApplication.userType==0){
                    if (bean.getChoice_kc().size() > 11 && position <= 11) {
                        videoadapter.setSelectItem(position);
                        videoadapter.notifyDataSetInvalidated();
                        MyApplication.videoitemid = bean.getChoice_kc().get(position).getId();
                        MyApplication.videoname=bean.getChoice_kc().get(position).getName();
                        videoId = bean.getChoice_kc().get(position).getMv_url();
                        polyvPermission.applyPermission(getActivity(), PolyvPermission.OperationType.play);
                        polyvPermission.setResponseCallback(new PolyvPermission.ResponseCallback() {
                            @Override
                            public void callback() {
                                requestPermissionWriteSettings();
                            }
                        });
                    } else if (bean.getChoice_kc().size() < 11 && position <= 6) {
                        videoadapter.setSelectItem(position);
                        videoadapter.notifyDataSetInvalidated();
                        MyApplication.videoname=bean.getChoice_kc().get(position).getName();
                        MyApplication.videoitemid = bean.getChoice_kc().get(position).getId();
                        videoId = bean.getChoice_kc().get(position).getMv_url();
                        polyvPermission.applyPermission(getActivity(), PolyvPermission.OperationType.play);
                        polyvPermission.setResponseCallback(new PolyvPermission.ResponseCallback() {
                            @Override
                            public void callback() {
                                requestPermissionWriteSettings();
                            }
                        });
                    } else {
                        dialog = new QMUITipDialog.Builder(getContext()).setIconType(4).setTipWord("未购买课程无法学习").create();
                        dialog.show();
                        mhandler.sendEmptyMessageDelayed(1,1500);
                    }

                }else if (MyApplication.userType==1){
                    videoadapter.setSelectItem(position);
                    videoadapter.notifyDataSetInvalidated();
                    MyApplication.videoname=bean.getChoice_kc().get(position).getName();
                    MyApplication.videoitemid = bean.getChoice_kc().get(position).getId();
                    videoId = bean.getChoice_kc().get(position).getMv_url();
                    polyvPermission.applyPermission(getActivity(), PolyvPermission.OperationType.play);
                    polyvPermission.setResponseCallback(new PolyvPermission.ResponseCallback() {
                        @Override
                        public void callback() {
                            requestPermissionWriteSettings();
                        }
                    });
                }


            }
        });
        }catch (Exception e){
            Toast.makeText(getContext(), "数据异常", Toast.LENGTH_SHORT).show();
        }
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
