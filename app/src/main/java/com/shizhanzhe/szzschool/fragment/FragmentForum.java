package com.shizhanzhe.szzschool.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shizhanzhe.szzschool.Bean.ForumBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.ForumBKActivity;
import com.shizhanzhe.szzschool.activity.ForumItemActivity;
import com.shizhanzhe.szzschool.activity.KTListActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.adapter.ForumBKAdapter;
import com.shizhanzhe.szzschool.adapter.ForumLVAdapter;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.MyListView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


/**
 * Created by hasee on 2016/10/26.
 */
@ContentView(R.layout.fragment_forum)
public class FragmentForum extends Fragment {
    @ViewInject(R.id.gv_bk)
    MyGridView bk;
    @ViewInject(R.id.gv_forum)
    MyListView gv;
    List<ForumBean.LtmodelBean> ltmodel;
    List<ForumBean.SzanBean> szan;
    AlertDialog alertDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("未开通VIP,无法查看");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getdata();
        bk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String fid = ltmodel.get(position).getFid();
                    String name = ltmodel.get(position).getName();
                    Intent intent = new Intent();
                    intent.setClass(getActivity(),ForumBKActivity.class);
                    intent.putExtra("fid",fid);
                    intent.putExtra("name",name);
                    startActivity(intent);

            }
        });
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (szan.get(position).getFid().equals("58")&&MyApplication.vip.equals("1")){
                    String title = szan.get(position).getSubject();
                    String name = szan.get(position).getRealname();
                    String time = szan.get(position).getDateline();
                    String pid = szan.get(position).getPid();
                    String fid = szan.get(position).getFid();
                    String logo = szan.get(position).getLogo();
                    String rep = szan.get(position).getAlltip();
                    OkHttpDownloadJsonUtil.downloadJson(getContext(), "http://shizhanzhe.com/index.php?m=pcdata.add_num&pc=1&uid=" + MyApplication.myid + "&pid=" + pid + "+&token=" + MyApplication.token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                        @Override
                        public void onsendJson(String json) {

                        }
                    });
                    Intent intent = new Intent(getActivity(), ForumItemActivity.class);
                    intent.putExtra("pid",pid);
                    intent.putExtra("title",title);
                    intent.putExtra("name",name);
                    intent.putExtra("img",logo);
                    intent.putExtra("time",time);
                    intent.putExtra("rep",rep);
                    intent.putExtra("fid",fid);
                    startActivity(intent);
                }else {
                    alertDialog.show();
                }

            }
        });
    }
    private void getdata() {
        OkHttpDownloadJsonUtil.downloadJson(getContext(), Path.FORUMHOME(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd")
                        .create();
                ltmodel = gson.fromJson(json, ForumBean.class).getLtmodel();
                szan = gson.fromJson(json, ForumBean.class).getSzan();
                ForumBKAdapter bkadapter = new ForumBKAdapter(getContext(), ltmodel);
                ForumLVAdapter lvadapter = new ForumLVAdapter(getContext(), szan);
                bk.setAdapter(bkadapter);
                gv.setAdapter(lvadapter);
            }
        });
    }


}
