package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.ProBean2;
import com.shizhanzhe.szzschool.Bean.VideoBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.utils.Path;


import java.util.List;

/**
 * Created by zz9527 on 2017/6/27.
 */

public class Videoadapter extends BaseAdapter{
    private final LayoutInflater inflater;
    List<VideoBean> list;
   public  Videoadapter(Context context, List<VideoBean> list){
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
            convertView=inflater.inflate(R.layout.adapter_video,null);
            holder=new ViewHolder();
            holder.iv= (ImageView) convertView.findViewById(R.id.iv_demo);
            holder.num= (TextView) convertView.findViewById(R.id.tv_seri);
            holder.title= (TextView) convertView.findViewById(R.id.tv_title);
            holder.time= (TextView) convertView.findViewById(R.id.tv_time);

            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        VideoBean bean = list.get(position);
        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage(Path.IMG(MyApplication.proimg), holder.iv, MyApplication.displayoptions);
        holder.num.setText("第"+(position+1)+"课时");
        holder.title.setText(bean.getName());
        holder.time.setText(bean.getKc_hours());
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView num;
        TextView title;
        TextView time;
    }
}
