package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shizhanzhe.szzschool.Bean.CommentBean;
import com.shizhanzhe.szzschool.R;

import java.util.List;

/**
 * Created by Admin on 2016/5/27.
 */
public class ReplyAdapter extends BaseAdapter {
    private int resourceId;
    private List<CommentBean.ReplyBean> list;
    private LayoutInflater inflater;
    private TextView replyContent;
    private SpannableString ss;
    private Context context;
    public ReplyAdapter(Context context, List<CommentBean.ReplyBean> list
            , int resourceId){
        this.list = list;
        this.context = context;
        this.resourceId = resourceId;
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
        CommentBean.ReplyBean bean = list.get(position);
        convertView = inflater.inflate(resourceId, null);
        replyContent = (TextView)
                convertView.findViewById(R.id.replyContent);

        final String replyNickName = bean.getUsername();
        String replyContentStr = bean.getContent();
        //用来标识在 Span 范围内的文本前后输入新的字符时是否把它们也应用这个效果
        //Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)
        //Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)
        //Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)
        //Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)
        ss = new SpannableString(replyNickName+"回复"
                +"："+replyContentStr);
        ss.setSpan(new ForegroundColorSpan(Color.BLUE),0,
                replyNickName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.BLUE),replyNickName.length(),
                replyNickName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //为回复的人昵称添加点击事件
        replyContent.setText(ss);
        //添加点击事件时，必须设置
        replyContent.setMovementMethod(LinkMovementMethod.getInstance());
        return convertView;
    }



    private final class ListItemClick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
