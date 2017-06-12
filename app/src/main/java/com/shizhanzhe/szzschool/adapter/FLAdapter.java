package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.FLBean;
import com.shizhanzhe.szzschool.Bean.SearchBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.AnimateFirstDisplayListener;

import org.xutils.x;

import java.util.List;

import static com.shizhanzhe.szzschool.activity.MyApplication.displayoptions;

/**
 * Created by hasee on 2016/12/23.
 */

public class FLAdapter extends BaseAdapter{
    private final LayoutInflater inflater;
    private List<FLBean> list;
    public FLAdapter(Context context, List<FLBean> list) {
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
        ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.fl_gv,null);
            holder = new ViewHolder();
            holder.iv= (ImageView) convertView.findViewById(R.id.fl_gv_iv);
            holder.tv= (TextView) convertView.findViewById(R.id.fl_gv_tv);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage(Path.IMG(list.get(position).getImg()), holder.iv, displayoptions, new AnimateFirstDisplayListener());
        holder.tv.setText(list.get(position).getTitle());
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}