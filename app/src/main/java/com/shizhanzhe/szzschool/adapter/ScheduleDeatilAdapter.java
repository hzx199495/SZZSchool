package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shizhanzhe.szzschool.Bean.ScheduleBean;
import com.shizhanzhe.szzschool.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2017/8/17.
 */

public class ScheduleDeatilAdapter extends BaseAdapter{
    private final LayoutInflater inflater;
    private List<ScheduleBean.InfoBean.KcDataBean.VdataBean> list;
    public ScheduleDeatilAdapter(Context context, List<ScheduleBean.InfoBean.KcDataBean.VdataBean> list){
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
            convertView=inflater.inflate(R.layout.adapter_scheduledetail,null);
            holder=new ViewHolder();
            holder.tv= (TextView) convertView.findViewById(R.id.title);
            holder.jd= (TextView) convertView.findViewById(R.id.jd);
            convertView.setTag(holder);
        }else {
            holder= (ScheduleDeatilAdapter.ViewHolder) convertView.getTag();
        }
        ScheduleBean.InfoBean.KcDataBean.VdataBean bean = list.get(position);
        holder.tv.setText(bean.getVtitle());
        if (bean.getVdetail().getGuantime()!=null&&bean.getVdetail().getAddtime()!=null) {
            double b = Double.parseDouble(bean.getVdetail().getGuantime()) / Double.parseDouble(bean.getVdetail().getVtime())*100;
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            if (nf.format(b).contains("NaN")){
                holder.jd.setText("进度：0%");
            }else {
                holder.jd.setText("进度："+ nf.format(b)+ "%");
            }

        }else {
            holder.jd.setText("进度："+"0%");
        }
        return convertView;
    }
    class ViewHolder{
        TextView tv;
        TextView jd;
    }
}
