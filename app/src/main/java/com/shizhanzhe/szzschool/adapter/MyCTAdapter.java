package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.MyCTBean;
import com.shizhanzhe.szzschool.Bean.TGMoney;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import java.util.List;

import static com.shizhanzhe.szzschool.activity.MyApplication.displayoptions;

/**
 * Created by zz9527 on 2017/3/14.
 */

public class MyCTAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<MyCTBean> list;
    private Context context;
    public MyCTAdapter(Context context, List<MyCTBean> list) {
        this.list = list;
        this.context=context;
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
            convertView = inflater.inflate(R.layout.adapter_myct, null);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.num = (TextView) convertView.findViewById(R.id.num);
            holder.state = (TextView) convertView.findViewById(R.id.state);
            holder.tgmoney = (Button) convertView.findViewById(R.id.tgmoney);
            holder.study = (Button) convertView.findViewById(R.id.study);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MyCTBean bean = list.get(position);
        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage(Path.IMG(bean.getThumb()), holder.iv, displayoptions);
        holder.title.setText(bean.getTitle());
        holder.time.setText("结算时间：" + bean.getEndtime());
        holder.num.setText("参团人数：" + bean.getTynum());
        holder.state.setText("本团状态："+bean.getStr());
        if (bean.getStr().contains("开团成功，待处理")){
            holder.tgmoney.setVisibility(View.VISIBLE);
            holder.study.setVisibility(View.GONE);
        }
        if (bean.getStr().contains("团购成功")){
            holder.tgmoney.setVisibility(View.GONE);
            holder.study.setVisibility(View.VISIBLE);
        }
        holder.tgmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpDownloadJsonUtil.downloadJson(context, new Path(context).TGMONEY( bean.getKtid()), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        Gson gson = new Gson();
                        TGMoney tgMoney = gson.fromJson(json, TGMoney.class);
                        String info = tgMoney.getInfo();
                        if (info.contains("成功")){
                            new SVProgressHUD(context).showSuccessWithStatus(info);
                        }else {
                            new SVProgressHUD(context).showInfoWithStatus(info);
                        }
                    }
                });
            }
        });
        holder.study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, DetailActivity.class);
                intent.putExtra("id", bean.getSid());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView iv;
        TextView title;
        TextView time;
        TextView num;
        TextView state;
        Button tgmoney;
        Button study;
    }
}
