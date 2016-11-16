package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.os.Bundle;

import com.shizhanzhe.szzschool.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by hasee on 2016/11/10.
 */
@ContentView(R.layout.dialog_find_pass)
public class FindPWActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
}
