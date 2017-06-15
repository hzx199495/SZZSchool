package com.shizhanzhe.szzschool.video;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.easefun.polyvsdk.sub.vlms.entity.PolyvQuestionInfo;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.CommentBean;
import com.shizhanzhe.szzschool.Bean.ForumCommentBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.Path;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class PolyvTalkListViewAdapter extends BaseAdapter {
    private Activity context;
    private List<CommentBean> lists;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;
    private DisplayImageOptions options;
    private SpannableString ss;
    public PolyvTalkListViewAdapter(Activity context, List<CommentBean>  lists) {
        this.context = context;
        this.lists = lists;
        this.inflater = LayoutInflater.from(context);
        this.options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.polyv_avatar_def) // resource
                // or
                // drawable
                .showImageForEmptyUri(R.drawable.polyv_avatar_def) // resource or
                // drawable
                .showImageOnFail(R.drawable.polyv_avatar_def) // resource or drawable
                .bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true).cacheOnDisk(true)
                .build();
    }
    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.polyv_listview_talk_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            viewHolder.tv_msg=(TextView) convertView.findViewById(R.id.tv_msg);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.sublv_talk = (LinearListView) convertView.findViewById(R.id.sublv_talk);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final int pPosition = position;
        final CommentBean bean = lists.get(pPosition);
        List<CommentBean.ReplyBean> reply = bean.getReply();
        if (reply.size() > 0) {
            viewHolder.adapter = new PolyvSubTalkListViewAdapter(context, reply);
            viewHolder.sublv_talk.setVisibility(View.VISIBLE);
            viewHolder.sublv_talk.setAdapter(viewHolder.adapter);
            viewHolder.sublv_talk.setOnItemClickListener(new LinearListView.OnItemClickListener() {

                @Override
                public void onItemClick(LinearListView parent, View view, int position, long id) {
                    Intent intent = new Intent(context, PolyvTalkEdittextActivity.class);
                    intent.putExtra("questionid", bean.getId());
                    intent.putExtra("questionunameid", bean.getUid());
                    // 由于接口只能回复发表讨论的人，故这里使用发表讨论的人的昵称
                    intent.putExtra("nickname", bean.getUsername());
                    context.startActivityForResult(intent, 13);
                }
            });
        } else {
            viewHolder.sublv_talk.setVisibility(View.GONE);
        }

        ss = new SpannableString(bean.getUsername()
                +"："+bean.getContent());
        ss.setSpan(new ForegroundColorSpan(Color.BLUE),0,
                bean.getUsername().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.BLUE),bean.getUsername().length(),
                bean.getUsername().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //为回复的人昵称添加点击事件
        viewHolder.tv_msg.setText(ss);
        //添加点击事件时，必须设置
        viewHolder.tv_msg.setMovementMethod(LinkMovementMethod.getInstance());
        viewHolder.tv_time.setText(getSpaceTime(bean.getAddtime()));
        if(Path.IMG(bean.getLogo()).contains("http")) {
            ImageLoader.getInstance().displayImage(bean.getLogo(), viewHolder.iv_avatar, options);
        }else {
            ImageLoader.getInstance().displayImage(Path.IMG(bean.getLogo()), viewHolder.iv_avatar, options);
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView iv_avatar;
        TextView tv_msg;
        TextView tv_time;
        LinearListView sublv_talk;
        PolyvSubTalkListViewAdapter adapter;
    }
    public static String getSpaceTime(Long millisecond) {
        long currentMillisecond = System.currentTimeMillis();
        //间隔秒
        Long spaceSecond = (currentMillisecond - millisecond*1000) / 1000;
        //一分钟之内
        if (spaceSecond >= 0 && spaceSecond < 60) {
            return "刚刚";
        }
        //一小时之内
        else if (spaceSecond / 60 > 0 && spaceSecond / 60 < 60) {
            return spaceSecond / 60 + "分钟之前";
        }
        //一天之内
        else if (spaceSecond / (60 * 60) > 0 && spaceSecond / (60 * 60) < 24) {
            return spaceSecond / (60 * 60) + "小时之前";
        }
        //3天之内
        else if (spaceSecond/(60*60*24)>0&&spaceSecond/(60*60*24)<3){
            return spaceSecond/(60*60*24)+"天之前";
        }else {
            return getDateTimeFromMillisecond(millisecond);
        }
    }
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millisecond*1000);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
}
