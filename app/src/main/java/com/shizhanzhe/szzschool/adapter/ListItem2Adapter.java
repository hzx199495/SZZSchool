package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.LoginActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.utils.Path;

import java.util.List;

import static com.shizhanzhe.szzschool.activity.MyApplication.displayoptions;

/**
 * Created by zz9527 on 2017/7/14.
 */

public class ListItem2Adapter extends BaseAdapter {
    private List<ProBean.TxBean> list;
    private LayoutInflater inflater;
    private Context context;

    public ListItem2Adapter(List<ProBean.TxBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    DisplayImageOptions options = new DisplayImageOptions.Builder()
            // 是否设置为圆角，弧度为多少，当弧度为90时显示的是一个圆
            .displayer(new RoundedBitmapDisplayer(15))
            .showImageOnLoading(R.drawable.img_load)
            .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
            .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
            .build();

    @Override
    public int getCount() {
        if (list.size() % 2 > 0) {
            return list.size() / 2 + 1;
        } else {
            return list.size() / 2;
        }
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
            convertView = inflater.inflate(R.layout.adapter_listitem2, null);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            holder.tv = (TextView) convertView.findViewById(R.id.tv);
            holder.iv2 = (ImageView) convertView.findViewById(R.id.iv2);
            holder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
            holder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
            holder.open = (ImageView) convertView.findViewById(R.id.open);
            holder.open2 = (ImageView) convertView.findViewById(R.id.open2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.open.setVisibility(View.GONE);
        int distance = list.size() - position * 2;
        int cellCount = distance >= 2 ? 2 : distance;
        final List<ProBean.TxBean> bean = list.subList(position * 2, position * 2 + cellCount);
        if (bean.size() > 0) {
            holder.tv.setText(bean.get(0).getStitle());
            ImageLoader imageloader = ImageLoader.getInstance();
            imageloader.displayImage(Path.IMG(bean.get(0).getThumb()), holder.iv, options);
            if (bean.get(0).getStatus().equals("1")) {
                holder.open.setVisibility(View.VISIBLE);
            }
            if (bean.size() > 1) {
                holder.tv2.setText(bean.get(1).getStitle());
                ImageLoader imageloader2 = ImageLoader.getInstance();
                imageloader2.displayImage(Path.IMG(bean.get(1).getThumb()), holder.iv2, options);
                if (bean.get(1).getStatus().equals("1")) {
                    holder.open2.setVisibility(View.VISIBLE);
                }
            } else {
                holder.ll.setVisibility(View.INVISIBLE);
            }
        }
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (bean.get(0).getStatus().equals("0")) {
                        Intent intent = new Intent();
                        intent.setClass(context, DetailActivity.class);
                        String proid = bean.get(0).getId();
                        intent.putExtra("id", proid);
                        context.startActivity(intent);
                    }else if (bean.get(0).getStatus().equals("1")) {
                        Toast.makeText(context, "课程未开放", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        holder.iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (bean.get(1).getStatus().equals("0")) {
                        Intent intent = new Intent();
                        intent.setClass(context, DetailActivity.class);
                        String proid = bean.get(1).getId();
                        intent.putExtra("id", proid);
                        context.startActivity(intent);
                    }else if (bean.get(1).getStatus().equals("1")) {
                        Toast.makeText(context, "课程未开放", Toast.LENGTH_SHORT).show();
                    }

            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView iv;
        TextView tv;
        ImageView iv2;
        TextView tv2;
        LinearLayout ll;
        ImageView open;
        ImageView open2;
    }
}
