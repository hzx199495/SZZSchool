package com.shizhanzhe.szzschool.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.Bean.VideoBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.ExamActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.utils.Path;


import java.util.List;

/**
 * Created by zz9527 on 2017/6/27.
 */

public class Videoadapter extends BaseAdapter {
    private final LayoutInflater inflater;
    List<ProDeatailBean.CiBean.ChoiceKcBean> list;
    Context context;
    String txId;
    int type;
    public Videoadapter(Context context, List<ProDeatailBean.CiBean.ChoiceKcBean> list, String txId,int type) {
        this.list = list;
        this.context = context;
        this.txId = txId;
        this.type=type;
        inflater = LayoutInflater.from(context);
    }
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            // 是否设置为圆角，弧度为多少，当弧度为90时显示的是一个圆
            .displayer(new RoundedBitmapDisplayer(15))
            .showImageOnLoading(R.drawable.img_load)
            .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
            .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
            .build();
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (txId.equals("0")) {
            ViewHolder2 holder2;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.videoadapter2, null);
                holder2 = new ViewHolder2();
                holder2.title = (TextView) convertView.findViewById(R.id.video_tv);
                holder2.time = (TextView) convertView.findViewById(R.id.video_time);
                holder2.select = (ImageView) convertView.findViewById(R.id.select);
                holder2.imgtime = (ImageView) convertView.findViewById(R.id.imgtime);
                convertView.setTag(holder2);
            } else {
                holder2 = (ViewHolder2) convertView.getTag();
            }
            final ProDeatailBean.CiBean.ChoiceKcBean bean = list.get(position);
            holder2.title.setText(bean.getName());
            holder2.time.setText(bean.getKc_hours());
            if (position == selectItem) {
                holder2.title.setTextColor(context.getResources().getColor(R.color.selectyellow));
                holder2.select.setVisibility(View.VISIBLE);
                holder2.imgtime.setImageResource(R.drawable.timeselect);
                holder2.time.setTextColor(context.getResources().getColor(R.color.selectyellow));
            } else {
                holder2.select.setVisibility(View.INVISIBLE);
                holder2.imgtime.setImageResource(R.drawable.time);
                holder2.title.setTextColor(Color.BLACK);
                holder2.time.setTextColor(context.getResources().getColor(R.color.green_color));
            }
        } else {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.adapter_video, null);
                holder = new ViewHolder();
                holder.iv = (ImageView) convertView.findViewById(R.id.iv_demo);
                holder.title = (TextView) convertView.findViewById(R.id.tv_title);
                holder.time = (TextView) convertView.findViewById(R.id.tv_time);
                holder.exam = (ImageView) convertView.findViewById(R.id.exam);
                holder.free = (TextView) convertView.findViewById(R.id.free);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final ProDeatailBean.CiBean.ChoiceKcBean bean = list.get(position);
            ImageLoader imageloader = ImageLoader.getInstance();
            imageloader.displayImage(Path.IMG(bean.getPicture()), holder.iv, options);
            holder.title.setText(bean.getName());
            holder.time.setText(bean.getKc_hours());
            if (type==2) {
                if (list.size() > 11) {
                    if (position > 11) {
                        holder.free.setVisibility(View.GONE);
                    } else {
                        holder.free.setVisibility(View.VISIBLE);
                    }
                } else if (list.size() < 11) {
                    if (position > 6) {
                        holder.free.setVisibility(View.GONE);
                    } else {
                        holder.free.setVisibility(View.VISIBLE);
                    }
                }
            }
            if(position==0){
                holder.exam.setVisibility(View.GONE);
            }else {
                holder.exam.setVisibility(View.VISIBLE);
                holder.exam.setImageResource(getId(bean.getGrade()));
                holder.exam.setOnClickListener(getId(bean.getGrade())==R.drawable.starexam?new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                            intent.setClass(context, ExamActivity.class);
                            intent.putExtra("videoId", bean.getId());
                            intent.putExtra("txId", txId);
                            context.startActivity(intent);
                    }
                }:null);
            }


//            if (position!=0) {
//                if (grade.contains("0")) {
//                    temp.setImageResource(R.drawable.notstudy);
//                    temp.setOnClickListener(null);
//                } else if (grade.contains("1")) {
//                    temp.setImageResource(R.drawable.edstudy);
//                } else{
//                    temp.setImageResource(R.drawable.starexam);
//                    temp.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent();
//                            intent.setClass(context, ExamActivity.class);
//                            intent.putExtra("videoId", bean.getId());
//                            intent.putExtra("txId", txId);
//                            context.startActivity(intent);
//                        }
//                    });
//                }
//            }else{
//            }
//            holder.exam=temp;
        }
        return convertView;
    }
    private int getId(String s){
        int i=-1;
        switch (s){
            case "0":
                i=R.drawable.notstudy;
                break;
            case "1":
                i=R.drawable.edstudy;
                break;
            case "2":
                i=R.drawable.starexam;
                break;

        }

        return i;
    }

    class ViewHolder {
        ImageView iv;
        TextView title;
        TextView time;
        ImageView exam;
        TextView free;
    }

    class ViewHolder2 {
        TextView title;
        TextView time;
        ImageView imgtime;
        ImageView select;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    private int selectItem = -1;
}
