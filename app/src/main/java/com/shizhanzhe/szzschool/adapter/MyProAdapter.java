package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easefun.polyvsdk.sub.auxilliary.cache.image.ImageLoader;
import com.shizhanzhe.szzschool.Bean.MyProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.Path;

import java.util.List;
import java.util.UnknownFormatConversionException;
import java.util.zip.Inflater;

import static com.shizhanzhe.szzschool.activity.MyApplication.displayoptions;

/**
 * Created by zz9527 on 2017/6/13.
 */

public class MyProAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    List<MyProBean> list;
    public MyProAdapter(List<MyProBean> list, Context context) {
        inflater = LayoutInflater.from(context);
        this.list=list;
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
            convertView=inflater.inflate(R.layout.fragment_mypro,null);
            holder=new ViewHolder();
            holder.iv= (ImageView) convertView.findViewById(R.id.iv);
            holder.title= (TextView) convertView.findViewById(R.id.title);
            holder.totletime= (TextView) convertView.findViewById(R.id.totletime);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        List<MyProBean.SysinfoBean> bean = list.get(position).getSysinfo();
        holder.title.setText(bean.get(0).getStitle());
        holder.totletime.setText("时长： "+bean.get(0).getSys_hours());
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(Path.IMG(bean.get(0).getThumb()), holder.iv, displayoptions);
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView title;
        TextView totletime;
    }
}
