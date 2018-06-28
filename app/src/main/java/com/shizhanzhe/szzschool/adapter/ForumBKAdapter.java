package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.shizhanzhe.szzschool.Bean.ForumBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.Path;

import java.util.List;


/**
 * Created by hasee on 2016/12/28.
 */

public class ForumBKAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private List<ForumBean.LtmodelBean> list;
    public ForumBKAdapter(Context context,List<ForumBean.LtmodelBean> list) {
        this.list=list;
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
            convertView=inflater.inflate(R.layout.fl_gv,null);
            holder = new ViewHolder();
            holder.iv= (ImageView) convertView.findViewById(R.id.fl_gv_iv);
            holder.tv= (TextView) convertView.findViewById(R.id.fl_gv_tv);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage(Path.IMG(list.get(position).getImgurl()), holder.iv, options);

        holder.tv.setText(list.get(position).getName());
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}
