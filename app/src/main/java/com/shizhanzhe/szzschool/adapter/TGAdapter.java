package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.shizhanzhe.szzschool.Bean.Image;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.Bean.TGBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.LoginActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.TGDetailActivity;
import com.shizhanzhe.szzschool.utils.Path;

import java.lang.annotation.ElementType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.shizhanzhe.szzschool.activity.MyApplication.displayoptions;

/**
 * Created by hasee on 2017/1/13.
 */

public class TGAdapter extends BaseAdapter {
    private List<TGBean> list;
    private List<ProBean.TgBean> list2;
    private LayoutInflater inflater;
    private Context context;
    private String ktagent;

    public TGAdapter(List<TGBean> list, List<ProBean.TgBean> list2, Context context, String ktagent) {
        if (list != null) {
            this.list = list;
        } else if (list2 != null) {
            this.list2 = list2;
        }
        this.ktagent = ktagent;
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
        if (list != null) {
            return list.size();
        } else if (list2 != null) {
            return list2.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        } else if (list2 != null) {
            return list2.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tg_gv, null);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.tg_iv);
            holder.title = (TextView) convertView.findViewById(R.id.tg_title);
            holder.yj = (TextView) convertView.findViewById(R.id.tg_yj);
            holder.tgj = (TextView) convertView.findViewById(R.id.tg_tgj);
            holder.ct = (ImageView) convertView.findViewById(R.id.tg_ct);
            holder.kt = (ImageView) convertView.findViewById(R.id.tg_kt);
            holder.start = (LinearLayout) convertView.findViewById(R.id.start);
            holder.end = (LinearLayout) convertView.findViewById(R.id.end);
            holder.gobuy = (ImageView) convertView.findViewById(R.id.gobuy);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (list != null) {
            final TGBean bean = list.get(position);
            ImageLoader imageloader = ImageLoader.getInstance();
            imageloader.displayImage(Path.IMG(bean.getThumb()), holder.iv, options);
            holder.title.setText(bean.getTitle());
            holder.yj.setText("原价：" + bean.getNowprice() + "元");
            String[] strs = bean.getPtmoney().split("\\|");
            String tgprice = "";
            for (int i = 0; i < strs.length; i++) {
                String[] strs2 = strs[i].split("-");
                tgprice += strs2[1] + "元/";
            }
            holder.tgj.setText("团：" + tgprice);
            if (bean.getKaikedata().contains("1")) {

                holder.kt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MyApplication.isLogin) {
                            if (ktagent.equals("1")) {
                                Intent intent = new Intent();
                                intent.setClass(context, TGDetailActivity.class);
                                intent.putExtra("tuanid", bean.getId());
                                intent.putExtra("type", 1);
                                context.startActivity(intent);
                            } else {
                                new SVProgressHUD(context).showInfoWithStatus("无开团权限");
                            }
                        } else {
                            Toast.makeText(context, "请先登录！", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, LoginActivity.class));

                        }
                    }

                });


                holder.ct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MyApplication.isLogin) {
                            Intent intent = new Intent();
                            intent.setClass(context, TGDetailActivity.class);
                            intent.putExtra("tuanid", bean.getId());
                            intent.putExtra("type", 2);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, "请先登录！", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, LoginActivity.class));

                        }

                    }
                });
            } else {
                holder.start.setVisibility(View.GONE);
                holder.end.setVisibility(View.VISIBLE);
                holder.gobuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(context, DetailActivity.class);
                        String proid = bean.getTxid();
                        intent.putExtra("id", proid);
                        context.startActivity(intent);

                    }

                });
            }
        } else if (list2 != null) {
            final ProBean.TgBean bean = list2.get(position);
            ImageLoader imageloader = ImageLoader.getInstance();
            imageloader.displayImage(Path.IMG(bean.getThumb()), holder.iv, options);
            holder.title.setText(bean.getTitle());
            holder.yj.setText("原价：" + bean.getNowprice() + "元");
            String[] strs = bean.getPtmoney().split("\\|");
            String tgprice = "";
            for (int i = 0; i < strs.length; i++) {
                String[] strs2 = strs[i].split("-");
                tgprice += strs2[1] + "元/";
            }
            holder.tgj.setText("团：" + tgprice);
            if (bean.getKaikedata().contains("1")) {

                holder.kt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MyApplication.isLogin) {
                            if (ktagent.equals("1")) {
                                Intent intent = new Intent();
                                intent.setClass(context, TGDetailActivity.class);
                                intent.putExtra("tuanid", bean.getId());
                                intent.putExtra("type", 1);
                                context.startActivity(intent);
                            } else {
                                new SVProgressHUD(context).showInfoWithStatus("无开团权限");
                            }
                        } else {
                            Toast.makeText(context, "请先登录！", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, LoginActivity.class));

                        }
                    }
                });


                holder.ct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MyApplication.isLogin) {
                            Intent intent = new Intent();
                            intent.setClass(context, TGDetailActivity.class);
                            intent.putExtra("tuanid", bean.getId());
                            intent.putExtra("type", 2);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, "请先登录！", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, LoginActivity.class));

                        }

                    }
                });
            } else {
                holder.start.setVisibility(View.GONE);
                holder.end.setVisibility(View.VISIBLE);
                holder.gobuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(context, DetailActivity.class);
                        String proid = bean.getTxid();
                        intent.putExtra("id", proid);
                        context.startActivity(intent);

                    }

                });
            }
        }

        return convertView;
    }

    class ViewHolder {
        LinearLayout end;
        LinearLayout start;
        ImageView iv;
        TextView title;
        TextView yj;
        TextView tgj;
        ImageView ct;
        ImageView kt;
        ImageView gobuy;
    }

    /**
     * 将字符串数据转化为毫秒数
     */
    public long time(String time) {


        try {
            String dateTime = time;

            Calendar c = Calendar.getInstance();
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime));
            return c.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
