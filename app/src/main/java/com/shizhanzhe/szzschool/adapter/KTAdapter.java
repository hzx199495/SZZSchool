package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhanzhe.szzschool.Bean.KTListBean;
import com.shizhanzhe.szzschool.R;

import java.util.List;

/**
 * Created by zz9527 on 2017/3/14.
 */

public class KTAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<KTListBean> list;
    String tgtitle;
    String img;
    Context context;

    public KTAdapter(Context context, List<KTListBean> list, String tgtitle, String img) {
        this.list = list;
        this.context = context;
        this.tgtitle = tgtitle;
        this.img = img;
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
            convertView = inflater.inflate(R.layout.adapter_ktlist, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final KTListBean bean = list.get(position);
        holder.name.setText(bean.getUname()+"的学习团,已有"+bean.getTynum()+"人参团");

        return convertView;
    }

    class ViewHolder {
        TextView name;
        ImageView img;
    }
}
