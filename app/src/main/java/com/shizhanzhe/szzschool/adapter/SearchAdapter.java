package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shizhanzhe.szzschool.Bean.SearchBean;
import com.shizhanzhe.szzschool.R;

import java.util.List;


/**
 * Created by yetwish on 2015-05-11
 */
public class SearchAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Context context;
    private List<SearchBean.TxBean> Txlist;
    private List<SearchBean.TzBean> Tzlist;
    public SearchAdapter(Context context, List<SearchBean.TxBean> data1,List<SearchBean.TzBean> data2) {
        this.context = context;
        if (data1!=null){
            this.Txlist = data1;
        }

        if (data2!=null){
            this.Tzlist = data2;
        }
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (Txlist!=null){
            return Txlist.size();
        }
        if (Tzlist!=null){
            return Tzlist.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (Txlist!=null){
            return Txlist.get(position);
        }
        if (Tzlist!=null){
            return Tzlist.get(position);
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_bean_list,null);
            holder=new ViewHolder();
            holder.tv= (TextView) convertView.findViewById(R.id.item_search_title);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        if (Txlist!=null){
            holder.tv.setTextColor(Color.RED);
            holder.tv.setText(Txlist.get(position).getStitle());
        }
        if (Tzlist!=null){
            holder.tv.setTextColor(Color.BLUE);
            holder.tv.setText(Tzlist.get(position).getSubject());
        }
        return convertView;
    }
    class  ViewHolder{
        TextView tv;
    }
}

