package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.IjkVideoActicity;

import java.util.List;

/**
 * Created by hasee on 2016/11/17.
 */
public class ListAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private List<String> proname;
    private List<String> url;
    private List<String> pid;
    String uid;
    String token;
    String sid;
    String spid;
    private Context context;

    public ListAdapter(Context context, List<String> proname, List<String> url, List<String> pid, String uid, String token,String sid,String spid) {
        this.context = context;
        this.proname = proname;
        this.url = url;
        this.uid = uid;
        this.token = token;
        this.pid = pid;
        this.sid=sid;
        this.spid=spid;
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
            holder.btn = (Button) convertView.findViewById(R.id.btn_play);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(proname.get(position));
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment= Path.COMMENT(pid.get(position),uid,token);
                IjkVideoActicity.intentTo(context, IjkVideoActicity.PlayMode.portrait, IjkVideoActicity.PlayType.vid, url.get(position),
                        false,comment,sid,spid,pid.get(position));
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv;
        Button btn;
    }
}
