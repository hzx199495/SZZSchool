package com.shizhanzhe.szzschool.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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

import com.google.gson.Gson;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.Image;
import com.shizhanzhe.szzschool.Bean.PersonalDataBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import org.zackratos.ultimatebar.UltimateBar;

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

import static com.mob.MobSDK.getContext;

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
    private QMUITipDialog mdialog;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mdialog.dismiss();
                    break;
            }
        }
    };
    private PersonalDataBean bean;
    private String sex = "";
    private Dialog dialog;

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    private String uid;
    private String mFile;
    private SharedPreferences.Editor editor;
    CityPickerView mPicker = new CityPickerView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        requestStoragePermission();
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(ContextCompat.getColor(this, R.color.top));
        mPicker.init(this);
        SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
        editor = preferences.edit();
        String img = preferences.getString("img", "");
        uid = preferences.getString("uid", "");
        if (img.contains("http")) {
            ImageLoader.getInstance().displayImage(img, setcv);
        } else {
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
                try {


                    Gson gson = new Gson();
                    bean = gson.fromJson(json, PersonalDataBean.class);
                    name.setText(bean.getRealname());
                    email.setText(bean.getEmail());
                    age.setText(bean.getAge());
                    location.setText(bean.getLocation_p() + bean.getLocation_c() + bean.getLocation_a());
                    location2.setText(bean.getAddress());
                    intro.setText(bean.getIntroduce());
                    mprovince = bean.getLocation_p();
                    mcity = bean.getLocation_c();
                    mdistrict = bean.getLocation_a();
                    if (bean.getSex().equals("0")) {
                        rg.check(R.id.nv);
                    } else if (bean.getSex().equals("1")) {
                        rg.check(R.id.nan);
                    } else if (bean.getSex().equals("2")) {
                        rg.check(R.id.other);
                    }
                } catch (Exception e) {
                    Toast.makeText(UserSetActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String mprovince;
    private String mcity;
    private String mdistrict;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_location:
                CityConfig cityConfig = new CityConfig.Builder()
                        .title("选择城市")//标题
                        .titleTextSize(18)//标题文字大小
                        .titleTextColor("#585858")//标题文字颜  色
                        .titleBackgroundColor("#E9E9E9")//标题栏背景色
                        .confirTextColor("#585858")//确认按钮文字颜色
                        .confirmText("确定")//确认按钮文字
                        .confirmTextSize(16)//确认按钮文字大小
                        .cancelTextColor("#585858")//取消按钮文字颜色
                        .cancelText("取消")//取消按钮文字
                        .cancelTextSize(16)//取消按钮文字大小
                        .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                        .showBackground(true)//是否显示半透明背景
                        .visibleItemsCount(5)//显示item的数量
                        .province(mprovince)//默认显示的省份
                        .city(mcity)//默认显示省份下面的城市
                        .district(mdistrict)//默认显示省市下面的区县数据
                        .provinceCyclic(true)//省份滚轮是否可以循环滚动
                        .cityCyclic(true)//城市滚轮是否可以循环滚动
                        .districtCyclic(true)//区县滚轮是否循环滚动
                        .drawShadows(false)//滚轮不显示模糊效果
                        .setLineColor("#03a9f4")//中间横线的颜色
                        .setLineHeigh(3)//中间横线的高度
                        .setShowGAT(true)//是否显示港澳台数据，默认不显示
                        .build();

                //设置自定义的属性配置
                mPicker.setConfig(cityConfig);
                //监听选择点击事件及返回结果
                mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //省份
                        if (province != null) {
                            mprovince = province.getName();

                        }

                        //城市
                        if (city != null) {
                            mcity = city.getName();
                        }

                        //地区
                        if (district != null) {
                            mdistrict = district.getName();
                        }
                        location.setText(mprovince + mcity + mdistrict);
                    }

                    @Override
                    public void onCancel() {
                        ToastUtils.showLongToast(UserSetActivity.this, "已取消");
                    }
                });

                //显示
                mPicker.showCityPicker();

                break;
            case R.id.back:
                this.finish();
                break;
            case R.id.user_save:
                try {

                    if ("".equals(sex)) {
                        sex = bean.getSex();
                    }
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("sex", sex)
                            .add("realname", name.getText().toString())
                            .add("age", age.getText().toString())
                            .add("address", location2.getText().toString())
                            .add("email", email.getText().toString())
                            .add("introduce", intro.getText().toString())
                            .add("location_p", mprovince)
                            .add("location_c", mcity)
                            .add("location_a", mdistrict)
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
                } catch (Exception e) {
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
                type = 2;

                //相册选择图片
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
                break;
            case R.id.tv_camera:
                dialog.dismiss();
                if (isSdcardExisting()) {
                    //相机
                    type = 1;
                    file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/test/" + IMAGE_FILE_NAME);
                    file.getParentFile().mkdirs();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Intent cameraIntent = new Intent(
                                "android.media.action.IMAGE_CAPTURE");
                        //改变Uri
                        uri = FileProvider.getUriForFile(this, "com.shizhanzhe.szzschool.fileProvider", file);
                        //添加权限
                        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                    } else {
                        Intent cameraIntent = new Intent(
                                "android.media.action.IMAGE_CAPTURE");
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                    }
                } else {
                    Toast.makeText(v.getContext(), "请插入sd卡", Toast.LENGTH_LONG)
                            .show();
                }
                break;
        }
    }

    private Uri uri;
    private File file;


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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            resizeImage(uri);
                        } else {
                            resizeImage(Uri.fromFile(file));
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "未找到存储卡，无法存储照片！",
                                Toast.LENGTH_LONG).show();
                    }
                    break;

                case RESIZE_REQUEST_CODE:
                    showResizeImage();
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
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        File out = new File(getPath());
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }



    //裁剪后的地址
    public String getPath() {
        //resize image to thumb
        if (mFile == null) {
            mFile = Environment.getExternalStorageDirectory().getAbsolutePath() +  "/test/outtemp.png";
        }
        return mFile;
    }

    int type = 0;

    private void showResizeImage() {
        Bitmap photo = BitmapFactory.decodeFile(mFile);
        setcv.setImageBitmap(photo);
        PostImg(photo);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            if (type == 1) {
//                Bitmap photo = BitmapFactory.decodeFile(file.getAbsolutePath());
//                Drawable drawable = new BitmapDrawable(photo);
//                setcv.setImageDrawable(drawable);
//                PostImg(photo);
//            } else if (type == 2) {
//                Bundle extras = data.getExtras();
//                if (extras != null) {
//                    Bitmap photo = extras.getParcelable("data");
//                    Drawable drawable = new BitmapDrawable(photo);
//                    setcv.setImageDrawable(drawable);
//                    PostImg(photo);
//                }
//            }
//
//        } else {
//            Bundle extras = data.getExtras();
//            if (extras != null) {
//                Bitmap photo = extras.getParcelable("data");
//                setcv.setImageBitmap(photo);
//                PostImg(photo);
//            }
//        }
    }

    private Uri getImageUri() {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }

    private void PostImg(Bitmap bm) {
        try {
            OkHttpClient client = new OkHttpClient();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("imgFile", "1.jpg", RequestBody.create(MediaType.parse("image/jpeg"), byteArrayOutputStream.toByteArray()));
            MultipartBody build = builder.build();

            okhttp3.Request bi = new okhttp3.Request.Builder()
                    .url("https://shizhanzhe.com/index.php?m=pcdata.uploadimg2&pc=1&uid=" + uid + "&dir=image")
                    .post(build)
                    .build();

            client.newCall(bi).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    mHandler.sendEmptyMessage(2);
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {

                    String s = response.body().string();
                    Gson gson = new Gson();
                    Image image = gson.fromJson(s, Image.class);
                    if (image.getUrl() == null) {
                        mHandler.sendEmptyMessage(2);
                    } else {
                        String url = image.getUrl();
                        editor.putString("img", url);
                        editor.commit();
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
            if (msg.what == 1) {
                mdialog = new QMUITipDialog.Builder(UserSetActivity.this).setIconType(4).setTipWord("修改成功").create();
                mdialog.show();
                mhandler.sendEmptyMessageDelayed(1, 1500);
            } else if (msg.what == 2) {
                mdialog = new QMUITipDialog.Builder(UserSetActivity.this).setIconType(4).setTipWord("修改失败").create();
                mdialog.show();
                mhandler.sendEmptyMessageDelayed(1, 1500);
            }
        }

    };
    /**
     * Android6.0后需要动态申请危险权限
     * 动态申请存储权限
     */
    private void requestStoragePermission() {

        int hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED){
            // 拥有权限，可以执行涉及到存储权限的操作
            Log.e("TAG", "你已经授权了该组权限");
        }else {
            // 没有权限，向用户申请该权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.e("TAG", "向用户申请该组权限");
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

    }
}
