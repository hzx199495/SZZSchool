package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.IjkVideoActicity;

import java.util.List;

/**
 * Created by hasee on 2016/11/17.
 */
public class ListAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private List<String> proname;
    private Context context;

    public ListAdapter(Context context, List<String> proname) {
        this.context = context;
        this.proname = proname;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return proname.size();
    }

    @Override
    public Object getItem(int position) {
        return proname.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_pro, null);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.prolisttv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText("第"+(position+1)+"节 "+proname.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView tv;
    }
}
