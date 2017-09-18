package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.Bean.QuestionBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.QuestionReplyAdapter;
import com.shizhanzhe.szzschool.utils.MyListView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.PolyvPlayerActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zz9527 on 2017/8/4.
 * 视频问答
 */
@ContentView(R.layout.fragment_videoquestion)
public class VideoQuestionAcitvity extends Activity {

    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.videoquestion)
    MyListView lv;
    @ViewInject(R.id.iv)
    ImageView iv;
    @ViewInject(R.id.questionTitle)
    TextView title;
    @ViewInject(R.id.questioner)
    TextView questioner;
    @ViewInject(R.id.time)
    TextView time;
    @ViewInject(R.id.toptitle)
    TextView toptitle;
    @ViewInject(R.id.pro)
    TextView pro;
    @ViewInject(R.id.noreply)
    TextView noreply;
    @ViewInject(R.id.rl_bot)
    RelativeLayout rl_bot;
    @ViewInject(R.id.dz)
    ImageView dz;
    private String teacher;
    private String uid;
    private String vip;
    String qid;

    SVProgressHUD svProgressHUD;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
        teacher = preferences.getString("teacher", "");
        vip = preferences.getString("vip", "");
        uid = preferences.getString("uid", "");
        qid =  getIntent().getStringExtra("qid");
        final String name =  getIntent().getStringExtra("name");
        toptitle.setText(name);
        pro.setText("相关课程："+name);
        getData(qid);
        dz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpDownloadJsonUtil.downloadJson(VideoQuestionAcitvity.this, "https://shizhanzhe.com/index.php?m=pcdata.dozan&qid=" + qid, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        if (json.contains("1")){
                            dz.setImageResource(R.drawable.dz_yes);
                        }
                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uid.contains(bean.getQinfo().get(0).getUid()) || teacher.contains("1")||vip.equals("1")) {
                    svProgressHUD.showWithStatus("加载中...");
                    OkHttpDownloadJsonUtil.downloadJson(VideoQuestionAcitvity.this, new Path(VideoQuestionAcitvity.this).SECOND(bean.getQinfo().get(0).getSid()), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                        @Override
                        public void onsendJson(String json) {
                            List<QuestionBean.QinfoBean> list = bean.getQinfo();
                            MyApplication.videojson = json;
                            MyApplication.txId = list.get(0).getSid();
                            MyApplication.videotypeid = list.get(0).getPid();
                            MyApplication.videoitemid = list.get(0).getCoid();
                            MyApplication.videotitle = name;
                            Gson gson = new Gson();
                            ProDeatailBean.TxBean tx = gson.fromJson(MyApplication.videojson, ProDeatailBean.class).getTx();
                            MyApplication.proimg = tx.getThumb();
                            List<ProDeatailBean.CiBean> bean = gson.fromJson(MyApplication.videojson, ProDeatailBean.class).getCi();
                            for (ProDeatailBean.CiBean b:bean){
                                if (b.getId().equals(list.get(0).getPid())){
                                    MyApplication.videotype=1;
                                    for (ProDeatailBean.CiBean.ChoiceKcBean c :
                                            b.getChoice_kc()) {
                                        if (c.getId().equals(list.get(0).getCoid())){
                                            Intent intent = PolyvPlayerActivity.newIntent(VideoQuestionAcitvity.this, PolyvPlayerActivity.PlayMode.portrait, c.getMv_url());
                                            startActivity(intent);
                                        }
                                    }

                                }
                            }
                        }
                    });
                }
            }
        });
        rl_bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uid.contains(bean.getQinfo().get(0).getUid()) || teacher.contains("1")) {
                    Intent intent = new Intent(VideoQuestionAcitvity.this, SendQuestionActivity.class);
                    intent.putExtra("type","2");
                    startActivityForResult(intent,1);
                }else {
                    new SVProgressHUD(VideoQuestionAcitvity.this).showInfoWithStatus("无权限回复!");
                }

            }
        });

    }

    QuestionBean bean;
    void getData(String qid) {
        svProgressHUD = new SVProgressHUD(this);
        svProgressHUD.showWithStatus("加载中...");
        OkHttpDownloadJsonUtil.downloadJson(this, Path.QUESTIONDETAIL(qid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                bean = gson.fromJson(json, QuestionBean.class);
                title.setText(bean.getQinfo().get(0).getAskquiz());
                ImageLoader.getInstance().displayImage(bean.getQinfo().get(0).getLogo(), iv);
                questioner.setText(bean.getQinfo().get(0).getRealname());
                time.setText(bean.getQinfo().get(0).getInputtime());
                List<QuestionBean.ReplyBean> replylist = bean.getReply();
                if (replylist.size()>0) {
                    noreply.setVisibility(View.GONE);
                    QuestionReplyAdapter adapter = new QuestionReplyAdapter(VideoQuestionAcitvity.this, replylist);
                    lv.setAdapter(adapter);
                }else {
                    noreply.setVisibility(View.VISIBLE);
                }
                svProgressHUD.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==11){
            OkHttpDownloadJsonUtil.downloadJson(VideoQuestionAcitvity.this, new Path(VideoQuestionAcitvity.this).ANSWERQUESTION(bean.getQinfo().get(0).getCoid(),bean.getQinfo().get(0).getUid() ,data.getStringExtra("msg"), qid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {
                    if (json.contains("1")) {
                        getData(qid);
                        Toast.makeText(VideoQuestionAcitvity.this, "回复成功！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(VideoQuestionAcitvity.this, "回复失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        svProgressHUD.dismiss();
    }
}
