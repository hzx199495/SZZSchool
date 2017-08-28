package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shizhanzhe.szzschool.Bean.QuestionBean;
import com.shizhanzhe.szzschool.R;

import java.util.List;

/**
 * Created by zz9527 on 2017/8/4.
 */

public class QuestionReplyAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    List<QuestionBean.ReplyBean> list;
    public QuestionReplyAdapter(Context context, List<QuestionBean.ReplyBean> list){
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
        ViewHolder holder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.adapter_reply,null);
            holder=new ViewHolder();
            holder.replyer= (TextView) convertView.findViewById(R.id.replyer);
            holder.replytv= (TextView) convertView.findViewById(R.id.replytv);
            holder.time= (TextView) convertView.findViewById(R.id.replytime);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        QuestionBean.ReplyBean bean = list.get(position);
        holder.replyer.setText(bean.getRealname());
        holder.replytv.setText(bean.getContent());
        holder.time.setText(bean.getInputtime());
        return convertView;
    }
    class ViewHolder{
        TextView replyer;
        TextView time;
        TextView replytv;
    }
}
