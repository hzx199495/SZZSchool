package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shizhanzhe.szzschool.R;

import org.jsoup.examples.ListLinks;

import java.util.ArrayList;

/**
 * Created by zz9527 on 2017/8/16.
 */

public class ScheduleAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private ArrayList<String> list;
    public ScheduleAdapter(Context context, ArrayList<String> list){
        this.list= list;
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
            convertView=inflater.inflate(R.layout.adapter_schedule,null);
            holder=new ViewHolder();
            holder.tv= (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(list.get(position));
        return convertView;
    }
    class ViewHolder{
        TextView tv;
    }
}

