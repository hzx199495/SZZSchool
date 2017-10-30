package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shizhanzhe.szzschool.Bean.PersonalDataBean;
import com.shizhanzhe.szzschool.Bean.QuestionListBean;
import com.shizhanzhe.szzschool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class QuestionListAdapter extends BaseAdapter {
    private Context mContext;
    private List<QuestionListBean> mList;
    private String name;
    public QuestionListAdapter(Context context, List<QuestionListBean> list, String name) {
        mContext = context;
        mList = list;
        this.name=name;
    }

    @Override
    public int getCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_questionlist, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.project = (TextView) view.findViewById(R.id.project);
            viewHolder.time = (TextView) view.findViewById(R.id.time);
            viewHolder.ok = (TextView) view.findViewById(R.id.ok);
            viewHolder.stay = (TextView) view.findViewById(R.id.stay);
            viewHolder.help = (TextView) view.findViewById(R.id.help);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        QuestionListBean bean = mList.get(position);
        viewHolder.title.setText(bean.getAskquiz());
        viewHolder.project.setText(name);
        viewHolder.time.setText(bean.getInputtime());
        if (bean.getIsdeal().contains("0")){
            viewHolder.stay.setVisibility(View.VISIBLE);
        }else{
            viewHolder.ok.setVisibility(View.VISIBLE);
        }
        viewHolder.help.setText(bean.getHelp()+"人觉得有帮助");
        return view;

    }

    static class ViewHolder {
        TextView title, project, time, ok, stay, help;
    }
}
