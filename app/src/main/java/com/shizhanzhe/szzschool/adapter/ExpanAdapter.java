package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.ExamActivity;

import java.util.List;

/**
 * Created by zz9527 on 2017/11/6.
 */

public class ExpanAdapter extends BaseExpandableListAdapter {
    private List<ProDeatailBean.CiBean> list;
    private Context context;
    private String catId;
    private String mian;
    public ExpanAdapter(Context context, List<ProDeatailBean.CiBean> list, String catId,String mian){
        this.context=context;
        this.list=list;
        this.catId=catId;
        this.mian=mian;
    }
    @Override
    public int getGroupCount() {
        if (catId.equals("41")){
            return list.size();
        }else{
           return 1;
        }
    }

    @Override
    public int getChildrenCount(int i) {
        return list.get(i).getChoice_kc().size();
    }

    @Override
    public Object getGroup(int i) {
        return list.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return list.get(i).getChoice_kc().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
            groupViewHolder=new GroupViewHolder();
            groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv);
            groupViewHolder.view= convertView.findViewById(R.id.view);
            groupViewHolder.iv = (ImageView) convertView.findViewById(R.id.view2);
            convertView.setTag(groupViewHolder);
        }  else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
    }
        final ProDeatailBean.CiBean bean = list.get(i);
        groupViewHolder.tvTitle.setText(bean.getCtitle());
        //判断isExpanded就可以控制是按下还是关闭，同时更换图片
        if(isExpanded){
            groupViewHolder.iv.setBackgroundResource(R.drawable.open);
            groupViewHolder.view.setBackgroundResource(R.color.open);
        }else{
            groupViewHolder.iv.setBackgroundResource(R.drawable.close);
            groupViewHolder.view.setBackgroundResource(R.color.close);
        }
        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_expand, viewGroup, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
            childViewHolder.time = (TextView) convertView.findViewById(R.id.tv_time);
            childViewHolder.free = (TextView) convertView.findViewById(R.id.free);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        final ProDeatailBean.CiBean.ChoiceKcBean bean = list.get(i).getChoice_kc().get(i1);
        childViewHolder.title.setText(bean.getName());
        childViewHolder.time.setText(bean.getKc_hours());
        if (catId.contains("41")){
            if (mian.contains(bean.getId())){
                childViewHolder.free.setVisibility(View.VISIBLE);
            }else {
                childViewHolder.free.setVisibility(View.GONE);
            }
        }else {
            if (i==0&&list.get(0).getChoice_kc().size() > 11) {
                if (i1 > 11) {
                    childViewHolder.free.setVisibility(View.GONE);
                } else {
                    childViewHolder.free.setVisibility(View.VISIBLE);
                }
            } else if (i==0&&list.get(0).getChoice_kc().size() < 11) {
                if (i1 > 6) {
                    childViewHolder.free.setVisibility(View.GONE);
                } else {
                    childViewHolder.free.setVisibility(View.VISIBLE);
                }
            }
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    static class GroupViewHolder {
        TextView tvTitle;
        View view;
        ImageView iv;
    }

    static class ChildViewHolder {
        TextView title;
        TextView time;
        TextView free;
    }
}
