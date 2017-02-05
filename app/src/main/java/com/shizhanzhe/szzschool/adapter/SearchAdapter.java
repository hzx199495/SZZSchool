package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shizhanzhe.szzschool.Bean.SearchBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.ViewHolder;
import com.shizhanzhe.szzschool.widge.CommonAdapter;

import org.xutils.x;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


/**
 * Created by yetwish on 2015-05-11
 */

//public class SearchAdapter extends CommonAdapter<SearchBean> {
//
//    public SearchAdapter(Context context, List<SearchBean> data, int layoutId) {
//        super(context, data, layoutId);
//    }
//
//    @Override
//    public void convert(ViewHolder holder, int position) {
//
//        holder.setText(R.id.item_search_tv_title, mData.get(position).getTitle());
//
//    }

public class SearchAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    Context context;
    List<SearchBean> list;
    public SearchAdapter(Context context, List<SearchBean> data) {
        this.context = context;
        this.list = data;
        inflater = LayoutInflater.from(context);
    }
//
//    @Override
//    public void convert(ViewHolder holder, int position) {
//        holder.setText(R.id.item_search_tv_title,mData.get(position).getName())
//                .setText(R.id.item_search_tv_content,mData.get(position).getAge()+"");
//    }

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
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_bean_list,null);
            holder=new ViewHolder();
            holder.tv= (TextView) convertView.findViewById(R.id.item_search_title);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(list.get(position).getTitle());
        return convertView;
    }
    class  ViewHolder{
        TextView tv;
    }
}

