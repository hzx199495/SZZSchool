package com.shizhanzhe.szzschool.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.shizhanzhe.szzschool.Bean.CommentBean;
import com.shizhanzhe.szzschool.Bean.ForumCommentBean;
import com.shizhanzhe.szzschool.R;

import com.shizhanzhe.szzschool.activity.ForumTalkEdittextActivity;
import com.shizhanzhe.szzschool.utils.NoScrollListView;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.LinearListView;
import com.shizhanzhe.szzschool.video.PolyvSubTalkListViewAdapter;
import com.shizhanzhe.szzschool.video.PolyvTalkEdittextActivity;
import com.shizhanzhe.szzschool.video.PolyvTalkListViewAdapter;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.shizhanzhe.szzschool.adapter.CommentAdapter.getSpaceTime;

/**
 * Created by hasee on 2017/1/3.
 */

public class ForumCommentAdapter extends BaseAdapter {
    private Activity context;
    private List<ForumCommentBean> lists;
    private LayoutInflater inflater;
    private ForumCommentAdapter.ViewHolder viewHolder;
    private DisplayImageOptions options;
    private SpannableString ss;
    public ForumCommentAdapter(Activity context, List<ForumCommentBean>  lists) {
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
            viewHolder = new ForumCommentAdapter.ViewHolder();
            viewHolder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            viewHolder.tv_msg=(TextView) convertView.findViewById(R.id.tv_msg);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.sublv_talk = (LinearListView) convertView.findViewById(R.id.sublv_talk);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ForumCommentAdapter.ViewHolder) convertView.getTag();
        }
        final int pPosition = position;
        final ForumCommentBean bean = lists.get(pPosition);
        List<ForumCommentBean.ManReplyBean> reply = bean.getMan_reply();
        if (reply.size() > 0) {
            viewHolder.adapter = new ForumCommentSubTalkListViewAdapter(context, reply);
            viewHolder.sublv_talk.setVisibility(View.VISIBLE);
            viewHolder.sublv_talk.setAdapter(viewHolder.adapter);
            viewHolder.sublv_talk.setOnItemClickListener(new LinearListView.OnItemClickListener() {

                @Override
                public void onItemClick(LinearListView parent, View view, int position, long id) {
                    Intent intent = new Intent(context, ForumTalkEdittextActivity.class);
                    intent.putExtra("questionid", bean.getId());
                    // 由于接口只能回复发表讨论的人，故这里使用发表讨论的人的昵称
                    intent.putExtra("nickname", bean.getAuthor());
                    context.startActivityForResult(intent, 13);
                }
            });
        } else {
            viewHolder.sublv_talk.setVisibility(View.GONE);
        }

        ss = new SpannableString(bean.getAuthor()
                +"："+bean.getComment());
        ss.setSpan(new ForegroundColorSpan(Color.BLUE),0,
                bean.getAuthor().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.BLUE),bean.getAuthor().length(),
                bean.getAuthor().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //为回复的人昵称添加点击事件
        viewHolder.tv_msg.setText(ss);
        //添加点击事件时，必须设置
        viewHolder.tv_msg.setMovementMethod(LinkMovementMethod.getInstance());
        viewHolder.tv_time.setText(getSpaceTime(bean.getDateline()));
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
        ForumCommentSubTalkListViewAdapter adapter;
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

