package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhanzhe.szzschool.Bean.BKBean;
import com.shizhanzhe.szzschool.Bean.ForumBean;
import com.shizhanzhe.szzschool.R;

import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by hasee on 2017/1/3.
 */

public class ForumBKLVAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    List<BKBean> list;

    public ForumBKLVAdapter(Context context, List<BKBean> list) {
        this.list = list;
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_forumlist, null);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.userimg);
            holder.title = (TextView) convertView.findViewById(R.id.bktitle);
            holder.vip = (TextView) convertView.findViewById(R.id.vip);
            holder.jh = (TextView) convertView.findViewById(R.id.jh);
            holder.user = (TextView) convertView.findViewById(R.id.user);
            holder.vip2 = (TextView) convertView.findViewById(R.id.vip2);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.where = (TextView) convertView.findViewById(R.id.where);
            holder.look = (TextView) convertView.findViewById(R.id.look);
            holder.rep = (TextView) convertView.findViewById(R.id.rep);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        BKBean bean = list.get(position);
        x.image().bind(holder.iv,bean.getLogo());
        holder.user.setText(bean.getRealname());
        holder.title.setText(bean.getSubject());
        holder.vip.setVisibility(View.GONE);
        holder.time.setText("时间:"+getDateTimeFromMillisecond(bean.getDateline()));
        holder.where.setText("地区:"+bean.getLocation_p()+"-"+bean.getLocation_c());
        holder.look.setText("查看:"+bean.getLooknum());
        holder.rep.setText("回复:"+bean.getAlltip());
        return convertView;
    }

    class ViewHolder {
        ImageView iv;
        TextView title;
        TextView vip;
        TextView jh;
        TextView user;
        TextView vip2;
        TextView time;
        TextView where;
        TextView look;
        TextView rep;
    }
    /**
     * 将毫秒转化成固定格式的时间
     * 时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param millisecond
     * @return
     */
    public static String getDateTimeFromMillisecond(long millisecond){
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(millisecond*1000);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
}