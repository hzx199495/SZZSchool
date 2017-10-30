package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shizhanzhe.szzschool.Bean.XFBean;
import com.shizhanzhe.szzschool.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hasee on 2016/12/19.
 */

public class XFAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<XFBean> list;
    public XFAdapter(Context context, List<XFBean> list) {
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
        if(convertView==null){
            convertView=inflater.inflate(R.layout.xf_adapter,null);
            holder=new ViewHolder();
            holder.dd= (TextView) convertView.findViewById(R.id.dd);
            holder.rq= (TextView) convertView.findViewById(R.id.rq);
            holder.lx= (TextView) convertView.findViewById(R.id.lx);
            holder.je= (TextView) convertView.findViewById(R.id.je);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        XFBean xfBean = list.get(position);
        holder.dd.setText("订单号:"+xfBean.getOid());
        holder.rq.setText(getDate(xfBean.getAddtime()));
        if(xfBean.getSourceType().equals("1")){
            holder.lx.setText("购买课程");
        }else if(xfBean.getSourceType().equals("2")){
            holder.lx.setText("充值");
        }else if(xfBean.getSourceType().equals("4")){
            holder.lx.setText("签到");
        }else if(xfBean.getSourceType().equals("5")){
            holder.lx.setText("团购");
        }else if(xfBean.getSourceType().equals("8")){
            holder.lx.setText("购买VIP");
        }
        holder.je.setText(xfBean.getIntegral());
        return convertView;
    }
    class ViewHolder{
        TextView dd;
        TextView rq;
        TextView lx;
        TextView je;
    }
    public String getDate(Long millisecond){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millisecond*1000);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
}
