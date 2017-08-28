package com.shizhanzhe.szzschool.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shizhanzhe.szzschool.Bean.Image;
import com.shizhanzhe.szzschool.Bean.NoteBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by zz9527 on 2017/7/27.
 */

public class NoteAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    List<NoteBean> list;
    Context context;
    int type;
    public NoteAdapter(Context context, List<NoteBean> list,int type){
        this.type=type;
        this.context=context;
        this.list=list;
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
        final viewHolder holder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.adapter_note,null);
            holder=new viewHolder();
            holder.time= (TextView) convertView.findViewById(R.id.time);
            holder.edit= (ImageView) convertView.findViewById(R.id.edit);
            holder.del= (ImageView) convertView.findViewById(R.id.del);
            holder.editText= (EditText) convertView.findViewById(R.id.edittext);
            holder.post= (TextView) convertView.findViewById(R.id.note_post);
            holder.title=(TextView) convertView.findViewById(R.id.title);
            holder.ll= (LinearLayout) convertView.findViewById(R.id.note_edit);
            holder.tv= (TextView) convertView.findViewById(R.id.note_tv);
            convertView.setTag(holder);
        }else {
            holder= (viewHolder) convertView.getTag();
        }
        final NoteBean noteBean = list.get(position);
        if (type==1){
            holder.title.setText(noteBean.getTitle());
        }

        holder.time.setText(noteBean.getAddtime().substring(0,10));
        holder.tv.setText(noteBean.getContent());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("删除");
                builder.setMessage("确认删除此笔记？");
                builder.setPositiveButton("立即删除", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        OkHttpDownloadJsonUtil.downloadJson(context, new Path(context).NOTELISTDEL(noteBean.getNid()), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                            @Override
                            public void onsendJson(String json) {
                                if (json.contains("1")){
                                    Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                                    list.remove(position);
                                    notifyDataSetChanged();
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });
                builder.create().show();

            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isVisibility = holder.ll.getVisibility();
                if (isVisibility==View.VISIBLE){
                    holder.ll.setVisibility(View.GONE);
                    holder.tv.setVisibility(View.VISIBLE);
                }else if (isVisibility==View.GONE){
                    holder.ll.setVisibility(View.VISIBLE);
                    holder.tv.setVisibility(View.GONE);
                    holder.editText.setText(noteBean.getContent());
                }
            }
        });
        holder.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv.setText(holder.editText.getText().toString());
                OkHttpDownloadJsonUtil.downloadJson(context, new Path(context).NOTELISTEDIT(noteBean.getNid(),holder.editText.getText().toString()), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        if (json.contains("1")){
                            Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
                            holder.ll.setVisibility(View.GONE);
                            holder.tv.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
        return convertView;
    }
    class viewHolder{
        TextView time;
        TextView tv;
        ImageView edit;
        ImageView del;
        EditText editText;
        TextView post;
        TextView title;
        LinearLayout ll;
    }
//    public static String getSpaceTime(Long millisecond) {
//        long currentMillisecond = System.currentTimeMillis();
//        //间隔秒
//        Long spaceSecond = (currentMillisecond - millisecond*1000) / 1000;
//        //一分钟之内
//        if (spaceSecond >= 0 && spaceSecond < 60) {
//            return "刚刚";
//        }
//        //一小时之内
//        else if (spaceSecond / 60 > 0 && spaceSecond / 60 < 60) {
//            return spaceSecond / 60 + "分钟之前";
//        }
//        //一天之内
//        else if (spaceSecond / (60 * 60) > 0 && spaceSecond / (60 * 60) < 24) {
//            return spaceSecond / (60 * 60) + "小时之前";
//        }
//        //3天之内
//        else if (spaceSecond/(60*60*24)>0&&spaceSecond/(60*60*24)<3){
//            return spaceSecond/(60*60*24)+"天之前";
//        }else {
//            return getDateTimeFromMillisecond(millisecond);
//        }
//    }
    /**
     * 将毫秒转化成固定格式的时间
     * 时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param millisecond
     * @return
     */
    public static String getDateTimeFromMillisecond(long millisecond){
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(millisecond*1000);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
}
