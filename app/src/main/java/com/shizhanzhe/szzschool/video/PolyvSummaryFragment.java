package com.shizhanzhe.szzschool.video;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.easefun.polyvsdk.sub.vlms.entity.PolyvCoursesInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.ProBean2;
import com.shizhanzhe.szzschool.Bean.VideoBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;

import java.util.List;

public class PolyvSummaryFragment extends Fragment {
    // 标题,价格,学习人数,简介,其他,展开
    private TextView tv_title, tv_money, tv_learn, tv_sum, tv_other, tv_expand;
    // fragmentView
    private View view;
    String title;
    String intro;

    private void findIdAndNew() {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_learn = (TextView) view.findViewById(R.id.tv_learn);
        tv_sum = (TextView) view.findViewById(R.id.tv_sum);
        tv_other = (TextView) view.findViewById(R.id.tv_other);
        tv_expand = (TextView) view.findViewById(R.id.tv_expand);
    }

    private void initView() {
        Gson gson = new Gson();
        final ProBean2.CiBean cibean= gson.fromJson(MyApplication.videojson, ProBean2.class).getCi();
        if (MyApplication.videotype==1){
            title=cibean.getA0().getCtitle();
            intro=cibean.getA0().getIntroduce();
        }else if (MyApplication.videotype==2){
            title=cibean.getA1().getCtitle();
            intro=cibean.getA1().getIntroduce();
        }else if (MyApplication.videotype==3){
            title=cibean.getA2().getCtitle();
            intro=cibean.getA2().getIntroduce();
        }else if (MyApplication.videotype==4){
            title=cibean.getA3().getCtitle();
            intro=cibean.getA3().getIntroduce();
        }else if (MyApplication.videotype==5){
            title=cibean.getA4().getCtitle();
            intro=cibean.getA4().getIntroduce();
        }

        tv_title.setText(title);
        tv_sum.setText(gson.fromJson(MyApplication.videojson, ProBean2.class).getTx().getIntroduce());
        tv_other.setText("暂无");
        tv_money.setText("付费");
        tv_money.setTextColor(getResources().getColor(R.color.center_right_text_color_green));
        tv_sum.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                Layout layout = tv_sum.getLayout();
                int lines = layout.getLineCount();
                if (lines > 3 || (lines > 0 && layout.getEllipsisCount(lines - 1) > 0)) {
                    tv_expand.setVisibility(View.VISIBLE);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    tv_sum.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    tv_sum.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        tv_sum.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (tv_expand.getVisibility() == View.GONE)
                    return;
                expandOrCollapse();
            }
        });
        tv_expand.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                expandOrCollapse();
            }
        });
    }

    private void expandOrCollapse() {
        if (!tv_expand.getText().equals("收起")) {
            tv_sum.setMaxLines(Integer.MAX_VALUE);
            tv_sum.setEllipsize(null);
            tv_expand.setText("收起");
        } else {
            tv_sum.setMaxLines(3);
            tv_sum.setEllipsize(TruncateAt.END);
            tv_expand.setText("展开");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (view == null)
            view = inflater.inflate(R.layout.polyv_fragment_tab_sum, container, false);
        findIdAndNew();
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null)
            ((ViewGroup) view.getParent()).removeView(view);
    }

}
