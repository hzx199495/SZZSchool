package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by hasee on 2016/10/26.
 */
public class BuddyAdapter extends BaseExpandableListAdapter {

    ArrayList<String> parent;
    ArrayList<String> parentImg=null;
    HashMap<String, List<String>> map;
    private Context context;
    private LayoutInflater inflater;

    public BuddyAdapter(ArrayList<String> parentImg,ArrayList<String> parent, HashMap<String, List<String>> map, Context context) {
        super();
        this.parentImg=parentImg;
        this.parent = parent;
        this.map = map;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    //得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String key = parent.get(groupPosition);
        return (map.get(key).get(childPosition));
    }

    //得到子item的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //设置子item的组件
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        String key = this.parent.get(groupPosition);
        String info = map.get(key).get(childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_children, null);
        }
        TextView tv = (TextView) convertView
                .findViewById(R.id.second_textview);
        tv.setText((childPosition+1)+"."+info);
        return tv;
    }

    //获取当前父item下的子item的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        String key = this.parent.get(groupPosition);
        int size=map.get(key).size();
        return size;
    }
    //获取当前父item的数据
    @Override
    public Object getGroup(int groupPosition) {
        return parent.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return parent.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    //设置父item组件
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_parent, null);
        }
        TextView tv = (TextView) convertView
                .findViewById(R.id.parent_textview);
        ImageView iv = (ImageView) convertView
                .findViewById(R.id.parent_imgview);
        tv.setText(this.parent.get(groupPosition));
        if (this.parentImg!=null) {
            x.image().bind(iv, Path.IMG(this.parentImg.get(groupPosition)));
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    // 子选项是否可以选择
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
