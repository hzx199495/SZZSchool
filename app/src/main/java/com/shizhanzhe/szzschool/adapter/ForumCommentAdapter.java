package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
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
import com.shizhanzhe.szzschool.Bean.ForumCommentBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.AnimateFirstDisplayListener;
import com.shizhanzhe.szzschool.video.NoScrollListView;


import java.util.List;

import static com.shizhanzhe.szzschool.adapter.CommentAdapter.getSpaceTime;

/**
 * Created by hasee on 2017/1/3.
 */

public class ForumCommentAdapter extends BaseAdapter {
    private int resourceId;
    private Context context;
    private Handler handler;
    private List<ForumCommentBean> list;
    private LayoutInflater inflater;
    private ViewHolder mholder = null;
    private DisplayImageOptions options;

    private android.view.animation.Animation animation;//动画效果的
    public void addData(List<ForumCommentBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    public ForumCommentAdapter(Context context, List<ForumCommentBean> list
            , int resourceId, Handler handler) {
        this.list = list;
        this.context = context;
        this.handler = handler;
        this.resourceId = resourceId;
        inflater = LayoutInflater.from(context);

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.bg_loading) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher) // 设置图片加载/解码过程中错误时候显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();// 构建完成
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
        ForumCommentBean commentBean = list.get(position);
        ViewHolder mholder = null;
        if (convertView == null) {
            mholder = new ViewHolder();
            convertView = inflater.inflate(resourceId, null);
            mholder.commentImage= (ImageView) convertView.findViewById(R.id.comment_iv);
            mholder.commentNickname = (TextView)
                    convertView.findViewById(R.id.comment_name);
            mholder.commentItemTime = (TextView)
                    convertView.findViewById(R.id.comment_time);
            mholder.commentItemPosition = (TextView)
                    convertView.findViewById(R.id.comment_position);
            mholder.commentItemContent = (TextView)
                    convertView.findViewById(R.id.comment_content);
            mholder.replyList = (NoScrollListView)
                    convertView.findViewById(R.id.no_scroll_list_reply);
            mholder.replyBut = (Button)
                    convertView.findViewById(R.id.but_comment_reply);
            convertView.setTag(mholder);
        } else {
            mholder = (ViewHolder) convertView.getTag();
        }

        mholder.commentNickname.setText(commentBean.getAuthor());
        mholder.commentItemTime.setText(getSpaceTime(commentBean.getDateline()));
        mholder.commentItemContent.setText(commentBean.getComment());
        mholder.commentItemPosition.setText((position+1)+"楼");
        ImageLoader imageloader = ImageLoader.getInstance();
        if(Path.IMG(list.get(position).getLogo()).contains("http")) {
            imageloader.displayImage(list.get(position).getLogo(), mholder.commentImage, options, new AnimateFirstDisplayListener());
        }else {
            imageloader.displayImage(Path.IMG(list.get(position).getLogo()), mholder.commentImage, options, new AnimateFirstDisplayListener());
        }
        if(commentBean.getMan_reply()!=null) {
            ForumReplyAdapter adapter = new ForumReplyAdapter(context, commentBean.getMan_reply(), R.layout.reply_item);
            mholder.replyList.setAdapter(adapter);
            TextviewClickListener tcl = new TextviewClickListener(position);
            mholder.replyBut.setOnClickListener(tcl);
        }
        return convertView;
    }

    private final class ViewHolder {
        public ImageView commentImage;            //评论人头像
        public TextView commentNickname;            //评论人昵称
        public TextView commentItemPosition;            //评论顺序
        public TextView commentItemTime;            //评论时间
        public TextView commentItemContent;         //评论内容
        public NoScrollListView replyList;          //评论回复列表
        public Button replyBut;                     //回复


    }

    /**
     * 获取回复评论
     */
    public void getReplyComment(ForumCommentBean.ManReplyBean bean, int position) {
        List<ForumCommentBean.ManReplyBean> rList = list.get(position).getMan_reply();
        rList.add(rList.size(), bean);
    }

    /**
     * 事件点击监听器
     */
    private final class TextviewClickListener implements View.OnClickListener {
        private int position;

        public TextviewClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.but_comment_reply:
                    handler.sendMessage(handler.obtainMessage(10, position));
                    break;
            }
        }
    }
}
