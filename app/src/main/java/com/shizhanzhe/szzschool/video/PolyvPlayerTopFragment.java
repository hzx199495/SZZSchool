package com.shizhanzhe.szzschool.video;

import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shizhanzhe.szzschool.R;


public class PolyvPlayerTopFragment extends Fragment implements View.OnClickListener {
    //fragmentView
    private View view;
    // 返回按钮
    private ImageView iv_finish;
    // 顶部布局
    private RelativeLayout rl_top;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.polyv_fragment_player_top, container, false);
        return view;
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (PolyvScreenUtils.isLandscape(getActivity())) {
            rl_top.setVisibility(View.GONE);
        } else {
            rl_top.setVisibility(View.VISIBLE);
        }
    }

    private void findIdAndNew() {
        iv_finish = (ImageView) view.findViewById(R.id.iv_finish);
        rl_top = (RelativeLayout) view.findViewById(R.id.rl_top);
    }

    private void initView() {
        iv_finish.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findIdAndNew();
        initView();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_finish:
                getActivity().finish();
                break;
        }
    }
}
