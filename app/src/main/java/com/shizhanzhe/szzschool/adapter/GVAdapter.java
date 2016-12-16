package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.Bean.SearchBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.db.DatabaseOpenHelper;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by hasee on 2016/11/17.
 */
public class GVAdapter extends BaseAdapter {
    private List<ProBean> list;
    private LayoutInflater inflater;
    private Context context;

    public GVAdapter(List<ProBean> list, Context context) {
        this.list = list;
        this.context=context;
        inflater = LayoutInflater.from(context);
    }
    public void GVAdapterClear(){
        DbManager manager = DatabaseOpenHelper.getInstance();
        try {
            manager.delete(SearchBean.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        list.clear();
        notifyDataSetChanged();
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
            convertView=inflater.inflate(R.layout.fl_gv,null);
             holder = new ViewHolder();
            holder.iv= (ImageView) convertView.findViewById(R.id.fl_gv_iv);
            holder.tv= (TextView) convertView.findViewById(R.id.fl_gv_tv);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        x.image().bind(holder.iv, Path.IMG(list.get(position).getThumb()), MyApplication.options);
        holder.tv.setText(list.get(position).getStitle());
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}
