package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.ForumBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.AnimateFirstDisplayListener;

import java.util.List;

import static com.shizhanzhe.szzschool.activity.MyApplication.displayoptions;

/**
 * Created by hasee on 2016/12/28.
 */

public class ForumBKAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    List<ForumBean.LtmodelBean> list;
    public ForumBKAdapter(Context context,List<ForumBean.LtmodelBean> list) {
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
        ViewHold holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.adapter_forumbk,null);
            holder=new ViewHold();
            holder.tv= (TextView) convertView.findViewById(R.id.bk_tv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHold) convertView.getTag();
        }
        ForumBean.LtmodelBean bean = list.get(position);
        holder.tv.setText(bean.getName());
        return convertView;
    }
    class  ViewHold{
        TextView tv;
    }

}
