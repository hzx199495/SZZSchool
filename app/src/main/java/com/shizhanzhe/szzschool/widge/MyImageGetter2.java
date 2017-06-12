package com.shizhanzhe.szzschool.widge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;

import com.shizhanzhe.szzschool.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hasee on 2017/1/9.
 */

public class MyImageGetter2 implements Html.ImageGetter {

    WeakReference<TextView> mTextViewReference;
    Context mContext;

    public MyImageGetter2(Context context, TextView textView) {
        mContext = context.getApplicationContext();
        mTextViewReference = new WeakReference<TextView>(textView);
    }

    @Override
    public Drawable getDrawable(String url) {

        URLDrawable urlDrawable = new URLDrawable(mContext);

        // 异步获取图片，并刷新显示内容
        new ImageGetterAsyncTask(url, urlDrawable).execute();

        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {

        WeakReference<URLDrawable> mURLDrawableReference;
        String mUrl;

        public ImageGetterAsyncTask(String url, URLDrawable drawable) {
            mURLDrawableReference = new WeakReference<URLDrawable>(drawable);
            mUrl = url;
        }
        @Override
        protected Drawable doInBackground(String... params) {

            // 下载图片
            try {
                URL url = new URL("http://www.shizhanzhe.com"+mUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoInput(true);
                conn.connect();
                if(conn.getResponseCode()==conn.HTTP_OK){
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);

                    Rect bounds = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

                    if (mURLDrawableReference.get() != null) {
                        mURLDrawableReference.get().setBounds(bounds);
                    }
                    bitmapDrawable.setBounds(bounds);
                    return bitmapDrawable;
                }
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Drawable result) {
            if (null != result) {
                if (mURLDrawableReference.get() != null) {
                    mURLDrawableReference.get().drawable = result;
                }
                if (mTextViewReference.get() != null) {
                    // 加载完一张图片之后刷新显示内容
                    mTextViewReference.get().setText(mTextViewReference.get().getText());
                }
            }
        }
    }


    public class URLDrawable extends BitmapDrawable {
        protected Drawable drawable;

        public URLDrawable(Context context) {
            // 设置默认大小和默认图片
            Rect bounds = new Rect(0, 0, 200, 200);
            setBounds(bounds);
            drawable = context.getResources().getDrawable(R.drawable.ic_launcher);
            drawable.setBounds(bounds);
        }

        @Override
        public void draw(Canvas canvas) {
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }
}
