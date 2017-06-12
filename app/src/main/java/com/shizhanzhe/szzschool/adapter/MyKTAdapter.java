package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.MyKTBean;
import com.shizhanzhe.szzschool.Bean.SearchBean;
import com.shizhanzhe.szzschool.Bean.TGsqlBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.db.DatabaseOpenHelper;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.List;

import static com.shizhanzhe.szzschool.activity.MyApplication.displayoptions;

/**
 * Created by zz9527 on 2017/3/14.
 */

public class MyKTAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<MyKTBean> list;
    DbManager manager = DatabaseOpenHelper.getInstance();

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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MyKTBean bean = list.get(position);

        try {
            List<TGsqlBean> tg = manager.selector(TGsqlBean.class).where("tuanid", "=", bean.getTuanid()).findAll();
            if (tg.size()>0) {
                ImageLoader imageloader = ImageLoader.getInstance();
                imageloader.displayImage(Path.IMG(tg.get(0).getImg()), holder.iv, displayoptions);
                holder.title.setText(tg.get(0).getTitle());
                holder.time.setText("结算：" + tg.get(0).getTime());
                holder.num.setText("参团人数：" + bean.getTynum());
            }
            return convertView;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv;
        TextView title;
        TextView time;
        TextView num;
    }
}
