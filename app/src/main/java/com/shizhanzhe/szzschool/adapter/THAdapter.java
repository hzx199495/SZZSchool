package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.shizhanzhe.szzschool.Bean.THBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.Path;

import java.util.List;

/**
 * Created by zz9527 on 2018/7/9.
 */
public class THAdapter extends BaseAdapter{

    private List<THBean.ShowBean> list;
    private LayoutInflater inflater;
    public THAdapter(List<THBean.ShowBean> list, Context context) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            // 是否设置为圆角，弧度为多少，当弧度为90时显示的是一个圆
            .displayer(new RoundedBitmapDisplayer(15))
            .showImageOnLoading(R.drawable.img_load)
            .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
            .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
            .build();
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
            convertView=inflater.inflate(R.layout.adapter_th,null);
            holder = new ViewHolder();
            holder.iv= (ImageView) convertView.findViewById(R.id.iv);
            holder.tv= (TextView) convertView.findViewById(R.id.tv);
            holder.price= (TextView) convertView.findViewById(R.id.price);
            holder.yjprice= (TextView) convertView.findViewById(R.id.yjprice);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage(Path.IMG(list.get(position).getThumb()), holder.iv, options);
        holder.tv.setText(list.get(position).getStitle());
        holder.price.setText("￥"+list.get(position).getSprice());
        holder.yjprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        holder.yjprice.setText("￥"+list.get(position).getYuanmoney());
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView tv;
        TextView price;
        TextView yjprice;
    }
}
