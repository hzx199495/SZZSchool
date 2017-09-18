package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shizhanzhe.szzschool.Bean.MyExamBean;
import com.shizhanzhe.szzschool.R;

import java.util.List;

/**
 * Created by zz9527 on 2017/9/15.
 */

public class MyExamAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    List<MyExamBean> list;
    public MyExamAdapter(Context context, List<MyExamBean> list){
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
        ViewHold holder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.adapter_myexam,null);
            holder=new ViewHold();
            holder.tv= (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        }else {
            holder= (ViewHold) convertView.getTag();
        }
        MyExamBean bean = list.get(position);
        holder.tv.setText(bean.getTitle());
        return convertView;
    }
    class ViewHold{
        TextView tv;
    }
}
