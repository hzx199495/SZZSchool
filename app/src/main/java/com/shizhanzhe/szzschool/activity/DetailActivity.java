package com.shizhanzhe.szzschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.fragment.FragmentDetail;
import com.shizhanzhe.szzschool.fragment.FragmentDetailProject;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by hasee on 2016/11/22.
 */
@ContentView(R.layout.activity_detail)
public class DetailActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String uid = intent.getStringExtra("uid");
        String token = intent.getStringExtra("token");
        String img = intent.getStringExtra("img");
        String intro = intent.getStringExtra("intro");
        String title = intent.getStringExtra("title");

        FragmentDetail fragmentDetail = new FragmentDetail().newInstance(img, title, intro);
        FragmentDetailProject fragmentDetailProject = new FragmentDetailProject().newInstance(id, uid, token);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.first, fragmentDetail).add(R.id.second, fragmentDetailProject)
                .commit();
    }
}
