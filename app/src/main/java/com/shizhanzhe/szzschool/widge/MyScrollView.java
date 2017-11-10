package com.shizhanzhe.szzschool.widge;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by zz9527 on 2017/11/8.
 */

public class MyScrollView extends ScrollView {

    private  MyScrollListener listener;
    private int scrollDistanceY;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                int newY = getScrollY();  //获取手指离开后再次更新获取的Y值
                if(newY != scrollDistanceY){ //如果不等与手指离开后的Y值，说明滑动还没停止，继续获取新值
                    scrollDistanceY = newY;  //将新值赋给参数
                    listener.sendDistanceY(scrollDistanceY);  //传递新值
                    handler.sendMessageDelayed(handler.obtainMessage(), 5);  //滑动没停止，再次获取
                }
            }
        }
    };

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface MyScrollListener{  //接口，用于传递滑动的Y值数据，activity实现该接口即可获取该值
        void sendDistanceY(int distance);
    }

    public void setMyScrollListener(MyScrollListener myScrollListener){  //绑定
        this.listener = myScrollListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {  //事件处理
        if(ev.getAction() == MotionEvent.ACTION_UP){  //如果手指离开，就30毫秒后再次请求，其他操作不影响，都传递滑动距离即可
            handler.sendEmptyMessageDelayed(1,30);  //30毫秒后再次执行，以防手指离开后还在继续滑动
        }
        scrollDistanceY = getScrollY();   //获取滑动的Y值
        listener.sendDistanceY(scrollDistanceY);//传递Y值
        return super.onTouchEvent(ev);
    }
}
