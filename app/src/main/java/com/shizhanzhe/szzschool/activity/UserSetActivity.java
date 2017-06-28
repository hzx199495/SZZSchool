package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.shizhanzhe.szzschool.Bean.PersonalDataBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hasee on 2016/11/25.
 */
@ContentView(R.layout.activity_userzl)
public class UserSetActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.tv_name)
    EditText name;
    @ViewInject(R.id.tv_rg)
    RadioGroup rg;
    @ViewInject(R.id.tv_age)
    EditText age;
    @ViewInject(R.id.tv_email)
    EditText email;
    @ViewInject(R.id.tv_location)
    TextView location;
    @ViewInject(R.id.tv_location2)
    TextView location2;
    @ViewInject(R.id.tv_qm)
    TextView intro;
    @ViewInject(R.id.user_save)
    TextView save;
    @ViewInject(R.id.back)
    ImageView back;
    PersonalDataBean bean;
    String sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        initView();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.nan) {
                    sex="1";
                } else if (checkedId == R.id.nv) {
                    sex="0";
                } else {
                    sex="2";
                }
            }
        });
        location.setOnClickListener(this);
        save.setOnClickListener(this);
        back.setOnClickListener(this);
    }
    void initView(){
        OkHttpDownloadJsonUtil.downloadJson(this, Path.PERSONALDATA(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                bean = gson.fromJson(json, PersonalDataBean.class);
                name.setText(bean.getRealname());
                email.setText(bean.getEmail());
                age.setText(bean.getAge());
                location.setText(bean.getLocation_p() + bean.getLocation_c() + bean.getLocation_a());
                location2.setText(bean.getAddress());
                intro.setText(bean.getIntroduce());
                province=bean.getLocation_p();
                city=bean.getLocation_c();
                district=bean.getLocation_a();
                if (bean.getSex().equals("0")) {
                    rg.check(R.id.nv);
                } else if (bean.getSex().equals("1")) {
                    rg.check(R.id.nan);
                } else if (bean.getSex().equals("2")) {
                    rg.check(R.id.other);
                }
            }
        });
    }
    String province;
    String city;
    String district;
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_location:
                if (!"".equals(bean.getLocation_a())&&!"".equals(bean.getLocation_p())&&!"".equals(bean.getLocation_c())){
                CityPicker cityPicker = new CityPicker.Builder(UserSetActivity.this)
                        .titleTextColor("#000000")
                        .backgroundPop(0xa0000000)
                        .province(province)
                        .city(district)
                        .district(bean.getLocation_a())
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(7)
                        .itemPadding(10)
                        .build();
                cityPicker.show();
                //监听方法，获取选择结果
                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        //省份
                         province = citySelected[0];
                        //城市
                         city = citySelected[1];
                        //区县（如果设定了两级联动，那么该项返回空）
                         district = citySelected[2];
                        location.setText(province + city + district);

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "已取消", Toast.LENGTH_LONG).show();
                    }
                });
            }
                break;
            case R.id.back:
                this.finish();
                break;
            case R.id.user_save:
                OkHttpClient client = new OkHttpClient();
                RequestBody body=new FormBody.Builder()
                        .add("sex", sex)
                        .add("realname",name.getText().toString())
                        .add("age",age.getText().toString())
                        .add("address",location2.getText().toString())
                        .add("email",email.getText().toString())
                        .add("introduce",intro.getText().toString())
                        .add("location_p",province)
                        .add("location_c",city)
                        .add("location_a",district)
                        .build();
                Request request=new Request.Builder()
                        .url(Path.PERSONALUPDATE(MyApplication.myid, MyApplication.token))
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("_________",e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                            if (response.body().string().contains("修改成功")){
                                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                            }
                    }
                });
        }
    }
}
