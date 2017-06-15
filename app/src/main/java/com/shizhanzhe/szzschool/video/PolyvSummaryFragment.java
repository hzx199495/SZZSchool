package com.shizhanzhe.szzschool.video;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easefun.polyvsdk.sub.vlms.entity.PolyvCoursesInfo;
import com.shizhanzhe.szzschool.R;

public class PolyvSummaryFragment extends Fragment {
    // 标题,价格,学习人数,简介,其他,展开
    private TextView tv_title, tv_money, tv_learn, tv_sum, tv_other, tv_expand;
    // fragmentView
    private View view;


    private void findIdAndNew() {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_learn = (TextView) view.findViewById(R.id.tv_learn);
        tv_sum = (TextView) view.findViewById(R.id.tv_sum);
        tv_other = (TextView) view.findViewById(R.id.tv_other);
        tv_expand = (TextView) view.findViewById(R.id.tv_expand);
    }

    private void initView() {
        tv_title.setText("标题");
        tv_learn.setText("人在学");
        tv_sum.setText("暂无");

        tv_other.setText("暂无");

        tv_money.setText("免费");
        tv_money.setTextColor(getResources().getColor(R.color.center_right_text_color_green));

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
