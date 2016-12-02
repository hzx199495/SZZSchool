package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.easefun.polyvsdk.demo.IjkVideoActicity;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.ViewHolder;

import java.util.List;

/**
 * Created by hasee on 2016/11/17.
 */
public class ListAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private List<String> list;
    private List<String> url;
    private Context context;
    public ListAdapter(Context context, List<String> name,List<String> url){
        this.context=context;
        this.list=name;
        this.url=url;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.activity_pro,null);
            holder=new ViewHolder();
            holder.tv= (TextView) convertView.findViewById(R.id.prolisttv);
            holder.btn= (Button) convertView.findViewById(R.id.btn_play);
            convertView.setTag(holder);
        }else{
           holder= (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(list.get(position));
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IjkVideoActicity.intentTo(context, 4, 1, url.get(position),
                        false);
            }
        });
        return convertView;
    }
    class  ViewHolder{
        TextView tv;
        Button btn;
    }
}
