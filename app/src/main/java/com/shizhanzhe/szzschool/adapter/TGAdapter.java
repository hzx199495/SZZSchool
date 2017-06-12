package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.Bean.SearchBean;
import com.shizhanzhe.szzschool.Bean.TGsqlBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.TGDetailActivity;
import com.shizhanzhe.szzschool.db.DatabaseOpenHelper;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.shizhanzhe.szzschool.activity.MyApplication.displayoptions;

/**
 * Created by hasee on 2017/1/13.
 */

public class TGAdapter extends BaseAdapter {
    private List<ProBean.TgBean> list;
    private LayoutInflater inflater;
    private Context context;
    DbManager manager = DatabaseOpenHelper.getInstance();
    public TGAdapter(List<ProBean.TgBean> list, Context context) {
        this.list = list;
        this.context=context;
        inflater = LayoutInflater.from(context);
    }
    public void TGAdapterClear(){
        list.clear();
        notifyDataSetChanged();
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
       ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.tg_gv,null);
            holder = new ViewHolder();
            holder.iv= (ImageView) convertView.findViewById(R.id.tg_iv);
            holder.title= (TextView) convertView.findViewById(R.id.tg_title);
            holder.yj= (TextView) convertView.findViewById(R.id.tg_yj);
            holder.tgj= (TextView) convertView.findViewById(R.id.tg_tgj);
            holder.kt= (Button) convertView.findViewById(R.id.tg_kt);
            holder.ct= (Button) convertView.findViewById(R.id.tg_ct);
            holder.start= (LinearLayout) convertView.findViewById(R.id.start);
            holder.end= (LinearLayout) convertView.findViewById(R.id.end);
            holder.gobuy= (Button) convertView.findViewById(R.id.gobuy);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final ProBean.TgBean bean = list.get(position);
        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage(Path.IMG(bean.getThumb()), holder.iv, displayoptions);
        holder.title.setText(bean.getTitle());
//        try {
//            List<SearchBean> pro = manager.selector(SearchBean.class).where("proId", "=", bean.getTxid()).findAll();
//            holder.yj.setText("原价："+ pro.get(0).getPrice()+"元");
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
        String tgprice="";
        String[]  strs=bean.getPtmoney().split("\\|");
        for (int i=0;i<strs.length;i++) {
            String[] strs2 = strs[i].split("-");
            tgprice+=strs2[1]+"元/";
        }
        holder.tgj.setText("团："+tgprice);
        if (time(list.get(position).getKaikedata())>System.currentTimeMillis()) {
        holder.kt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        List<SearchBean> proid = manager.selector(SearchBean.class).where("proid", "=", bean.getTxid()).findAll();
                        Intent intent = new Intent();
                        intent.setClass(context, TGDetailActivity.class);
                        intent.putExtra("title",proid.get(0).getTitle());
                        intent.putExtra("img",proid.get(0).getImg());
                        intent.putExtra("time",bean.getKaikedata());
                        intent.putExtra("intro",proid.get(0).getIntro());
                        intent.putExtra("yjprice",proid.get(0).getPrice());
                        intent.putExtra("id",proid.get(0).getId()+"");
                        intent.putExtra("tuanid",bean.getId()+"");
                        intent.putExtra("price","100");
                        intent.putExtra("type",1);
                        context.startActivity(intent);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                }
            });
            holder.ct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        List<SearchBean> proid = manager.selector(SearchBean.class).where("proid", "=", bean.getTxid()).findAll();
                        Intent intent = new Intent();
                        intent.setClass(context, TGDetailActivity.class);
                        intent.putExtra("title",proid.get(0).getTitle());
                        intent.putExtra("img",proid.get(0).getImg());
                        intent.putExtra("time",bean.getKaikedata());
                        intent.putExtra("intro",proid.get(0).getIntro());
                        intent.putExtra("yjprice",proid.get(0).getPrice());
                        intent.putExtra("id",proid.get(0).getId()+"");
                        intent.putExtra("tuanid",bean.getId()+"");
                        intent.putExtra("price",bean.getPtmoney());
                        intent.putExtra("type",2);
                        context.startActivity(intent);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                }
            });
        }else{
            holder.start.setVisibility(View.GONE);
            holder.end.setVisibility(View.VISIBLE);
            holder.gobuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        List<SearchBean> search = manager.selector(SearchBean.class).where("proid", "=", bean.getTxid()).findAll();
                        Intent intent = new Intent();
                        intent.setClass(context, DetailActivity.class);
                        String title=search.get(0).getTitle();
                        String img=search.get(0).getImg();
                        String intro=search.get(0).getIntro();
                        String proid = search.get(0).getProid();
                        String price=search.get(0).getPrice();
                        intent.putExtra("id", proid);
                        intent.putExtra("img",img);
                        intent.putExtra("title",title);
                        intent.putExtra("intro",intro);
                        intent.putExtra("price",price);
                        context.startActivity(intent);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        return convertView;
    }
    class ViewHolder{
        LinearLayout end;
        LinearLayout start;
        ImageView iv;
        TextView title;
        TextView yj;
        TextView tgj;
        Button kt;
        Button ct;
        Button gobuy;
    }
    /**
     * 将字符串数据转化为毫秒数
     */
    public long time(String time){


        try {
            String dateTime=time;

            Calendar c = Calendar.getInstance();
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime));
            return  c.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
            return 0;
    }
}
