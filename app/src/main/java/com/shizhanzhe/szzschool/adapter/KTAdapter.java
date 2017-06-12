package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.shizhanzhe.szzschool.Bean.KTListBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;

import java.util.List;

/**
 * Created by zz9527 on 2017/3/14.
 */

public class KTAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<KTListBean> list;
    String tgtitle;
Context context;
    public KTAdapter(Context context, List<KTListBean> list, String tgtitle) {
        this.list = list;
        this.context=context;
        this.tgtitle = tgtitle;
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
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.num = (TextView) convertView.findViewById(R.id.num);
            holder.join = (TextView) convertView.findViewById(R.id.join);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final KTListBean bean = list.get(position);
        holder.title.setText("团购课程：" + tgtitle);
        holder.name.setText("团长：" + bean.getUname());
        holder.num.setText("参团人数：" + bean.getTynum());
        holder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    OkHttpDownloadJsonUtil.downloadJson(context, "http://shizhanzhe.com/index.php?m=pcdata.cantuan&pc=1&ktid=" + bean.getId() + "&uid=" + MyApplication.myid + "&token=" + MyApplication.token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                        @Override
                        public void onsendJson(String json) {
                            if (json.indexOf("0")!=-1) {
                                Toast.makeText(context, "无此开团", Toast.LENGTH_SHORT).show();
                            } else if (json.indexOf("1")!=-1) {
                                Toast.makeText(context, "参团成功", Toast.LENGTH_SHORT).show();
                            } else if (json.indexOf("2")!=-1) {
                                Toast.makeText(context, "数据库操作失败", Toast.LENGTH_SHORT).show();
                            } else if (json.indexOf("3")!=-1) {
                                Toast.makeText(context, "已经参团", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView name;
        TextView num;
        TextView join;
    }
}
