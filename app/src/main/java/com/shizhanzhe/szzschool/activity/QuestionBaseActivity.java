package com.shizhanzhe.szzschool.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.fragment.QuestionListFragment;

/**
 * Created by Administrator on 2017/8/1.
 */

public class QuestionBaseActivity extends FragmentActivity {
    private String con;
    private QuestionListFragment questionListFragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_question);
        fm = getFragmentManager();
        con = getIntent().getExtras().getString("con", "");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setDefaultFragment();
    }

    private void setDefaultFragment() {

        if (questionListFragment == null) {
            questionListFragment = new QuestionListFragment();
        }
        Bundle b = new Bundle();
        b.putString("con", con);
        questionListFragment.setArguments(b);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.content_layout, questionListFragment);
        ft.commit();
    }


}
