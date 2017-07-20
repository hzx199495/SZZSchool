package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.MyKTBean;
import com.shizhanzhe.szzschool.R;

import com.shizhanzhe.szzschool.utils.Path;



import java.util.List;

import static com.shizhanzhe.szzschool.activity.MyApplication.displayoptions;

/**
 * Created by zz9527 on 2017/3/14.
 */

public class MyKTAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<MyKTBean> list;

    public MyKTAdapter(Context context, List<MyKTBean> list) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_mykt, null);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.num = (TextView) convertView.findViewById(R.id.num);
            holder.state = (TextView) convertView.findViewById(R.id.state);
            holder.tgmoney= (TextView) convertView.findViewById(R.id.tgmoney);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MyKTBean bean = list.get(position);
        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage(Path.IMG(bean.getThumb()), holder.iv, displayoptions);
        holder.title.setText(bean.getTitle());
        holder.time.setText("结算时间：" + bean.getEndtime());
        holder.num.setText("参团人数：" + bean.getTynum());
        holder.state.setText("本团状态："+bean.getStr());
        return convertView;
    }

    class ViewHolder {
        ImageView iv;
        TextView title;
        TextView time;
        TextView num;
        TextView state;
        TextView tgmoney;
    }
}
