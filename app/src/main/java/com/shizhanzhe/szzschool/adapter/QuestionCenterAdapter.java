package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shizhanzhe.szzschool.Bean.QuestionCenterBean;
import com.shizhanzhe.szzschool.R;

import java.util.List;

/**
 * Created by zz9527 on 2017/8/16.
 */

public class QuestionCenterAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    List<QuestionCenterBean> list;
    public QuestionCenterAdapter(Context context, List<QuestionCenterBean> list){
        this.list=list;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.adapter_questioncenter,null);
            holder=new ViewHolder();
            holder.tv= (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        QuestionCenterBean b = list.get(position);
        holder.tv.setText(b.getStitle());
        return convertView;
    }
    class ViewHolder{
        TextView tv;
    }
}
