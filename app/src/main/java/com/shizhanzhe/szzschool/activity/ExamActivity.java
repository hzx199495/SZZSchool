package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.Exam;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.StatusBarUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2017/6/30.
 * 考试
 */
@ContentView(R.layout.activity_exam)
public class ExamActivity extends Activity {
    @ViewInject(R.id.check_a)
    CheckBox a;
    @ViewInject(R.id.check_b)
    CheckBox b;
    @ViewInject(R.id.check_c)
    CheckBox c;
    @ViewInject(R.id.check_d)
    CheckBox d;
    @ViewInject(R.id.check_e)
    CheckBox e;
    @ViewInject(R.id.check_f)
    CheckBox f;
    @ViewInject(R.id.next)
    Button next;
    @ViewInject(R.id.question)
    TextView question;
    @ViewInject(R.id.back)
    ImageView back;

    private int position;
    private int totle;
    private  List<Exam> list;
    private ArrayList<String> myAnswer;
    private ArrayList<String> answerList;
    private  String id;
    private String sid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setStatusBarColor(this,R.color.white); }
         id = getIntent().getStringExtra("videoId");
        sid = getIntent().getStringExtra("txId");
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("提示");
        CompoundButton.OnCheckedChangeListener myOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setAnswer();
            }
        };
        a.setOnCheckedChangeListener(myOnCheckedChangeListener);
        b.setOnCheckedChangeListener(myOnCheckedChangeListener);
        c.setOnCheckedChangeListener(myOnCheckedChangeListener);
        d.setOnCheckedChangeListener(myOnCheckedChangeListener);
        e.setOnCheckedChangeListener(myOnCheckedChangeListener);
        f.setOnCheckedChangeListener(myOnCheckedChangeListener);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        answerList = new ArrayList<>();
        myAnswer = new ArrayList<String>();
        OkHttpDownloadJsonUtil.downloadJson(this, Path.EXAM(id), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                try {
                    Gson gson = new Gson();
                    list = gson.fromJson(json, new TypeToken<List<Exam>>() {
                    }.getType());
                    totle = list.size();
                    for (Exam bean :
                            list) {
                        answerList.add(bean.getAnswer());
                    }
                    if (list.size()>0){
                        selected();
                    }else {
                        Toast.makeText(ExamActivity.this, "暂无考核题目", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }catch (Exception e){
                    Toast.makeText(ExamActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
        final ArrayList<String> wrongList = new ArrayList<>();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wrong="";
                if (position+1 == totle) {
                    for (int i=0;i<answerList.size();i++){
                        if (!myAnswer.get(i).equals(answerList.get(i))) {
                            wrongList.add(i+1+"");
                        }
                    }
                    if (wrongList.size()==0){
                        dialog.setMessage("满分考核通过！");
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                success();

                            }
                        });
                        dialog.create().show();
                    }else {

                        for (String str:wrongList
                                ) {
                            wrong+=str+" ";
                        }
                        dialog.setMessage("答案错误,第"+wrong+"题错误，请重新考试");
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        dialog.create().show();
                    }
                } else {
                    if (myAnswer.size()==position+1){
                        position++;
                        selected();
                    }else{
                        Toast.makeText(ExamActivity.this ,"请选择答案",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

    void startExam() {
        int count=0;
        if (position+1 == totle) {
            next.setText("提交答案");
        }
        Exam exam = list.get(position);
        String answer = exam.getAnswer();
        List<Exam.ExamBean> beanList = exam.getExam();
        for (Exam.ExamBean e:beanList
        ){
            if (e.getA3().contains(answer)){
                count++;
            }
        }
        if (exam.getExam().size() == 2) {
            question.setText("(单选题)" + (position + 1 + ".") + exam.getTitle()+"。");
            a.setText(exam.getExam().get(0).getA1() + "." + exam.getExam().get(0).getA2());
            b.setText(exam.getExam().get(1).getA1() + "." + exam.getExam().get(1).getA2());
            c.setVisibility(View.GONE);
            d.setVisibility(View.GONE);
            e.setVisibility(View.GONE);
            f.setVisibility(View.GONE);
        } else if (count==1 && exam.getExam().size() == 4) {
            question.setText("(单选题)" + (position + 1 + ".") + exam.getTitle());
            a.setText(exam.getExam().get(0).getA1() + "." + exam.getExam().get(0).getA2());
            b.setText(exam.getExam().get(1).getA1() + "." + exam.getExam().get(1).getA2());
            c.setText(exam.getExam().get(2).getA1() + "." + exam.getExam().get(1).getA2());
            d.setText(exam.getExam().get(3).getA1() + "." + exam.getExam().get(1).getA2());
            c.setVisibility(View.VISIBLE);
            d.setVisibility(View.VISIBLE);
            e.setVisibility(View.GONE);
            f.setVisibility(View.GONE);
        } else if (count>1 && exam.getExam().size() == 4) {
            question.setText("(多选题)" + (position + 1 + ".") + exam.getTitle());
            a.setText(exam.getExam().get(0).getA1() + "." + exam.getExam().get(0).getA2());
            b.setText(exam.getExam().get(1).getA1() + "." + exam.getExam().get(1).getA2());
            c.setText(exam.getExam().get(2).getA1() + "." + exam.getExam().get(1).getA2());
            d.setText(exam.getExam().get(3).getA1() + "." + exam.getExam().get(1).getA2());
            c.setVisibility(View.VISIBLE);
            d.setVisibility(View.VISIBLE);
            e.setVisibility(View.GONE);
            f.setVisibility(View.GONE);
        }else if (exam.getExam().size() == 5) {

            question.setText("(多选题)" + (position + 1 + ".") + exam.getTitle());
            a.setText(exam.getExam().get(0).getA1() + "." + exam.getExam().get(0).getA2());
            b.setText(exam.getExam().get(1).getA1() + "." + exam.getExam().get(1).getA2());
            c.setText(exam.getExam().get(2).getA1() + "." + exam.getExam().get(1).getA2());
            d.setText(exam.getExam().get(3).getA1() + "." + exam.getExam().get(1).getA2());
            e.setText(exam.getExam().get(4).getA1() + "." + exam.getExam().get(1).getA2());
            c.setVisibility(View.VISIBLE);
            d.setVisibility(View.VISIBLE);
            e.setVisibility(View.VISIBLE);

        } else if (exam.getExam().size() == 6) {
            question.setText("(多选题)" + (position + 1 + ".") + exam.getTitle());
            a.setText(exam.getExam().get(0).getA1() + "." + exam.getExam().get(0).getA2());
            b.setText(exam.getExam().get(1).getA1() + "." + exam.getExam().get(1).getA2());
            c.setText(exam.getExam().get(2).getA1() + "." + exam.getExam().get(1).getA2());
            d.setText(exam.getExam().get(3).getA1() + "." + exam.getExam().get(1).getA2());
            e.setText(exam.getExam().get(4).getA1() + "." + exam.getExam().get(1).getA2());
            f.setText(exam.getExam().get(5).getA1() + "." + exam.getExam().get(1).getA2());
            c.setVisibility(View.VISIBLE);
            d.setVisibility(View.VISIBLE);
            e.setVisibility(View.VISIBLE);
            f.setVisibility(View.VISIBLE);
        }
    }

    void setAnswer() {
        Exam exam = list.get(position);

        if (a.isChecked()) {
            myAnswer.add(exam.getExam().get(0).getA3());
        }
        if (b.isChecked()) {
            myAnswer.add(exam.getExam().get(1).getA3());
        }
        if (c.isChecked()) {
            myAnswer.add(exam.getExam().get(2).getA3());
        }
        if (d.isChecked()) {
            myAnswer.add(exam.getExam().get(3).getA3());
        }
        if (e.isChecked()) {
            myAnswer.add(exam.getExam().get(4).getA3());
        }
        if (f.isChecked()) {
            myAnswer.add(exam.getExam().get(5).getA3());
        }

    }

    void selected() {
        a.setChecked(false);
        b.setChecked(false);
        c.setChecked(false);
        d.setChecked(false);
        e.setChecked(false);
        f.setChecked(false);
        startExam();
    }
    void success(){
        OkHttpDownloadJsonUtil.downloadJson(this, new Path(this).EXAMSUCCESS(id,sid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                if (json.contains("成功保存")){
                    finish();
                }else{
                    Toast.makeText(ExamActivity.this,"服务器上传失败",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}

