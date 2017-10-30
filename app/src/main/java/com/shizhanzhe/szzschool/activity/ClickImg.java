package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by zz9527 on 2017/10/25.
 */
@ContentView(R.layout.click_img)
public class ClickImg extends Activity {
    @ViewInject(R.id.img)
    ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        this.setFinishOnTouchOutside(true);
        String url = getIntent().getStringExtra("url");
        if (url.contains("http")) {

        } else {
            url = "https://www.shizhanzhe.com" + url;
        }
        ImageLoader.getInstance().displayImage(url, iv, MyApplication.displayoptions);
    }
}
