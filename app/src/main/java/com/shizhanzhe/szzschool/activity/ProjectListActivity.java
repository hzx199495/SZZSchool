package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.Bean.THBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.GVAdapter;
import com.shizhanzhe.szzschool.adapter.THAdapter;
import com.shizhanzhe.szzschool.utils.StatusBarUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import static com.mob.MobSDK.getContext;

/**
 * Created by zz9527 on 2018/9/18.
 */
@ContentView(R.layout.activity_projectlist)
public class ProjectListActivity extends Activity {
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.gv)
    GridView gv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setStatusBarColor(this,R.color.white); }
        if (getIntent().getIntExtra("type",0)==1){
            title.setText("限时特惠");
            final List<THBean.ShowBean> listth =  (List<THBean.ShowBean>) getIntent().getSerializableExtra("listth");
            gv.setAdapter(new THAdapter(listth, this));
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.setClass(ProjectListActivity.this, DetailActivity.class);
                    String proid = listth.get(position).getId();
                    intent.putExtra("id", proid);
                    startActivity(intent);
                }
            });
        }else if (getIntent().getIntExtra("type",0)==2){
            title.setText("职业技能提升");
            final List<ProBean.TxBean> listts =  (List<ProBean.TxBean>) getIntent().getSerializableExtra("listts");
            gv.setAdapter(new GVAdapter(listts,this));
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (listts.get(position).getStatus().equals("0")) {
                        Intent intent = new Intent();
                        intent.setClass(ProjectListActivity.this, DetailActivity.class);
                        String proid = listts.get(position).getId();
                        intent.putExtra("id", proid);
                        startActivity(intent);
                    } else if (listts.get(position).getStatus().equals("1")) {
                        Toast.makeText(getContext(), "课程未开放", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
