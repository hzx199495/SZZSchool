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

import com.lljjcoder.citypickerview.widget.CityPicker;
import com.shizhanzhe.szzschool.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

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
    @ViewInject(R.id.user_save)
    TextView save;
@ViewInject(R.id.back)
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        name.setText(MyApplication.zh);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.nan){
                        rg.check(R.id.nan);
                }else if(checkedId == R.id.nv){
                    rg.check(R.id.nv);
                }else{
                    rg.check(R.id.other);
                }
            }
        });
        location.setOnClickListener(this);
        back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_location:
                CityPicker cityPicker = new CityPicker.Builder(UserSetActivity.this)
                        .titleTextColor("#000000")
                        .backgroundPop(0xa0000000)
                        .province("陕西省")
                        .city("西安市")
                        .district("雁塔区")
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
                        String province = citySelected[0];
                        //城市
                        String city = citySelected[1];
                        //区县（如果设定了两级联动，那么该项返回空）
                        String district = citySelected[2];
                        Log.i("_______",province+city+district);
                        location.setText(province+city+district);
//                        //邮编
//                        String code = citySelected[3];
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "已取消", Toast.LENGTH_LONG).show();
                    }
                });
                break;
//            case R.id.tv_close:
//
//                break;
//            case R.id.ll_name:
//                final Dialog dialog1 = new Dialog(this, R.style.Dialog_Fullscreen);
//                dialog1.setContentView(R.layout.dialog_edit_text);
//                dialog1.show();
//                break;
//            case R.id.ll_pass:
//                final Dialog dialog2 = new Dialog(this, R.style.Dialog_Fullscreen);
//                dialog2.setContentView(R.layout.dialog_change_pass);
//                dialog2.show();
//
////                EditText oldpass = (EditText) findViewById(R.id.et_old_pass);
//                final EditText newpass = (EditText) dialog.getWindow().findViewById(R.id.et_new_pass);
//                final EditText newpass2 = (EditText) dialog.getWindow().findViewById(R.id.et_new_pass_two);
//                dialog.getWindow().findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String p1 = newpass.getText().toString();
//                        String p2 = newpass2.getText().toString();
//                        if (p1.length() != 0 && p2.length() != 0) {
//                            if (p1.equals(p2)) {
//                                OkHttpDownloadJsonUtil.downloadJson(UserSetActivity.this, Path.CHANGE(MyApplication.zh, p1), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
//                                    @Override
//                                    public void onsendJson(String json) {
//                                        Gson gson = new Gson();
//                                        RegisterBean bean = gson.fromJson(json, RegisterBean.class);
//                                        if (bean.getStatus() == 1) {
//                                            Toast.makeText(UserSetActivity.this, bean.getInfo(), Toast.LENGTH_SHORT).show();
//                                            dialog2.dismiss();
//                                            finish();
//                                        } else if (bean.getStatus() == 2) {
//                                            Toast.makeText(UserSetActivity.this, bean.getInfo(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                            } else {
//                                Toast.makeText(UserSetActivity.this, "密码输入不一致", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(UserSetActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                break;
            case R.id.back:
                this.finish();
                break;
            case R.id.user_save:
                this.finish();
        }
    }
}
