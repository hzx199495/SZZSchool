package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.Image;
import com.shizhanzhe.szzschool.Bean.PersonalDataBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hasee on 2016/11/25.
 * 个人资料
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
    @ViewInject(R.id.setcv)
    CircleImageView setcv;
    PersonalDataBean bean;
    String sex="";
    private Dialog dialog;

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    String uid;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);

        SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
        editor = preferences.edit();
        String img = preferences.getString("img", "");
        uid = preferences.getString("uid", "");
        if (img.contains("http")){
            ImageLoader.getInstance().displayImage(img, setcv);
        }else {
            ImageLoader.getInstance().displayImage(Path.IMG(img), setcv);
        }
        initView();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.nan) {
                    sex = "1";
                } else if (checkedId == R.id.nv) {
                    sex = "0";
                } else {
                    sex = "2";
                }
            }
        });
        dialog = new Dialog(UserSetActivity.this, R.style.dialog);
        dialog.setContentView(R.layout.dialog_change_head);
        Click(dialog);
        location.setOnClickListener(this);
        save.setOnClickListener(this);
        back.setOnClickListener(this);
        setcv.setOnClickListener(this);
    }
    public void Click(Dialog dialog) {
        TextView selectBtn2 = (TextView) dialog.getWindow().findViewById(R.id.tv_camera);
        TextView selectBtn1 = (TextView) dialog.getWindow().findViewById(R.id.tv_photo);
        TextView close = (TextView) dialog.getWindow().findViewById(R.id.tv_close);
        selectBtn1.setOnClickListener(this);
        selectBtn2.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    void initView() {
        OkHttpDownloadJsonUtil.downloadJson(this, new Path(this).PERSONALDATA(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
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
                province = bean.getLocation_p();
                city = bean.getLocation_c();
                district = bean.getLocation_a();
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
                if (!"".equals(bean.getLocation_a()) && !"".equals(bean.getLocation_p()) && !"".equals(bean.getLocation_c())) {
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
                try {
                    Toast.makeText(this,"正在上传数据...",Toast.LENGTH_LONG);
                    if ("".equals(sex)){
                        sex=bean.getSex();
                    }
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("sex", sex)
                            .add("realname", name.getText().toString())
                            .add("age", age.getText().toString())
                            .add("address", location2.getText().toString())
                            .add("email", email.getText().toString())
                            .add("introduce", intro.getText().toString())
                            .add("location_p", province)
                            .add("location_c", city)
                            .add("location_a", district)
                            .build();
                    Request request = new Request.Builder()
                            .url(new Path(this).PERSONALUPDATE())
                            .post(body)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            mHandler.sendEmptyMessage(2);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.body().string().contains("修改成功")) {
                                mHandler.sendEmptyMessage(1);
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.setcv:
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                lp.width = (int) (display.getWidth()); //设置宽度
                lp.gravity = Gravity.BOTTOM;
                dialog.getWindow().setAttributes(lp);
                dialog.show();
                break;
            case R.id.tv_photo:
                dialog.dismiss();
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
                break;
            case R.id.tv_camera:
                dialog.dismiss();
                if (isSdcardExisting()) {
                    Intent cameraIntent = new Intent(
                            "android.media.action.IMAGE_CAPTURE");
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
                    cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                } else {
                    Toast.makeText(v.getContext(), "请插入sd卡", Toast.LENGTH_LONG)
                            .show();
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    resizeImage(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (isSdcardExisting()) {
                        resizeImage(getImageUri());
                    } else {
                        Toast.makeText(getApplicationContext(), "未找到存储卡，无法存储照片！",
                                Toast.LENGTH_LONG).show();
                    }
                    break;

                case RESIZE_REQUEST_CODE:
                    if (data != null) {
                        showResizeImage(data);
                    }
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isSdcardExisting() {
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    private void showResizeImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            setcv.setImageDrawable(drawable);
            PostImg(photo);
        }
    }

    private Uri getImageUri() {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));

    }
    void PostImg(Bitmap bm){
        try {
            Toast.makeText(this,"正在上传图片...",Toast.LENGTH_LONG);
            OkHttpClient client = new OkHttpClient();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("imgFile","1.jpg", RequestBody.create(MediaType.parse("image/jpeg"),byteArrayOutputStream.toByteArray()))
                    ;
            MultipartBody build = builder.build();

            okhttp3.Request bi = new okhttp3.Request.Builder()
                    .url("https://shizhanzhe.com/index.php?m=pcdata.uploadimg2&pc=1&uid="+uid+"&dir=image")
                    .post(build)
                    .build();

            client.newCall(bi).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("____resultonFailure",e.toString());
                    mHandler.sendEmptyMessage(2);
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {

                    String s = response.body().string();
                    Log.e("____resultonResponse",s);
                    Gson gson = new Gson();
                    Image image = gson.fromJson(s, Image.class);
                    if (image.getUrl()==null){
                        mHandler.sendEmptyMessage(2);
                    }else {
                        String url = image.getUrl();
                        editor.putString("img",url);
                        editor.commit();
                        Log.e("url",url);
                        mHandler.sendEmptyMessage(1);
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1) {
            new SVProgressHUD(UserSetActivity.this).showSuccessWithStatus("修改成功！");
            }else if (msg.what==2) {
                new SVProgressHUD(UserSetActivity.this).showErrorWithStatus("修改失败！");
            }
        }
    };
}
