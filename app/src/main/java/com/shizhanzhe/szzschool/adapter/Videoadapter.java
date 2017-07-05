package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.ProBean2;
import com.shizhanzhe.szzschool.Bean.VideoBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.ExamActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.utils.Path;


import java.util.List;

/**
 * Created by zz9527 on 2017/6/27.
 */

public class Videoadapter extends BaseAdapter{
    private final LayoutInflater inflater;
    List<VideoBean> list;
    Context context;
    String txId;
   public  Videoadapter(Context context, List<VideoBean> list,String txId){
       this.list=list;
       this.context=context;
       this.txId=txId;
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
            holder.title= (TextView) convertView.findViewById(R.id.tv_title);
            holder.time= (TextView) convertView.findViewById(R.id.tv_time);
            holder.exam= (Button) convertView.findViewById(R.id.exam);
            holder.study= (Button) convertView.findViewById(R.id.study);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        final VideoBean bean = list.get(position);
        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage(Path.IMG(MyApplication.proimg), holder.iv, MyApplication.displayoptions);
        holder.title.setText(bean.getName());
        holder.time.setText(bean.getKc_hours());

        if (bean.getGrade().contains("0")){
            holder.study.setText("开始学习");
            holder.exam.setVisibility(View.GONE);
        }else if (bean.getGrade().contains("1")){
            holder.exam.setVisibility(View.VISIBLE);
            holder.exam.setText("考试通过");
            holder.study.setText("已学习");
        }else if (bean.getGrade().contains("2")){
            holder.study.setText("开始学习");
            holder.exam.setText("开始考试");
            holder.exam.setVisibility(View.VISIBLE);
        }
        if (txId.equals("0")){
            holder.study.setVisibility(View.GONE);
            holder.exam.setVisibility(View.GONE);
        }
        holder.exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ExamActivity.class);
                intent.putExtra("videoId",bean.getId());
                intent.putExtra("txId",txId);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView title;
        TextView time;
        Button exam;
        Button study;
    }
}
