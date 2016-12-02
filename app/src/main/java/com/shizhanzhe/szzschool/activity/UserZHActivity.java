package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.shizhanzhe.szzschool.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by hasee on 2016/11/4.
 */
@ContentView(R.layout.activity_userzh)
public class UserZHActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
    }
}
