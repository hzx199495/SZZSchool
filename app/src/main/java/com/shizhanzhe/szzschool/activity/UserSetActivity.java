package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.RegisterBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hasee on 2016/11/25.
 */
@ContentView(R.layout.activity_userzl)
public class UserSetActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.setcv)
    CircleImageView setcv;
    @ViewInject(R.id.ll_name)
    LinearLayout name;
    @ViewInject(R.id.ll_pass)
    LinearLayout pass;
    @ViewInject(R.id.ll_file)
    LinearLayout file;
    @ViewInject(R.id.user_logout)
    TextView logout;
    @ViewInject(R.id.tv_tel)
    TextView tv;
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private Dialog dialog;

    private static final String IMAGE_FILE_NAME = "header.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        setcv.setOnClickListener(this);
        name.setOnClickListener(this);
        pass.setOnClickListener(this);
        file.setOnClickListener(this);
        logout.setOnClickListener(this);
        tv.setText(MyApplication.zh);
        dialog = new Dialog(UserSetActivity.this,R.style.dialog);
        dialog.setContentView(R.layout.dialog_change_head);
        Click(dialog);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.setcv:
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                lp.width = (int)(display.getWidth()); //设置宽度
                lp.gravity = Gravity.BOTTOM;
                dialog.getWindow().setAttributes(lp);
                dialog.show();
                break;
            case R.id.tv_photo:

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
                break;
            case R.id.tv_camera:
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
            case R.id.tv_close:
                dialog.dismiss();
                break;
            case R.id.ll_name:
                final Dialog dialog1 = new Dialog(this,R.style.Dialog_Fullscreen);
                dialog1.setContentView(R.layout.dialog_edit_text);
                dialog1.show();
                break;
            case R.id.ll_pass:
                final Dialog dialog2 = new Dialog(this,R.style.Dialog_Fullscreen);
                dialog2.setContentView(R.layout.dialog_change_pass);
                dialog2.show();

                EditText oldpass = (EditText) findViewById(R.id.et_old_pass);
                final EditText newpass = (EditText) findViewById(R.id.et_new_pass);
                final EditText newpass2 = (EditText) findViewById(R.id.et_new_pass_two);
                findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String p1 = newpass.getText().toString();
                        String p2 = newpass2.getText().toString();
                        if (p1!=null&&p2!=null){
                            if(p1.equals(p2)){
                                OkHttpDownloadJsonUtil.downloadJson(UserSetActivity.this, Path.CHANGE(MyApplication.zh, p1), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                                    @Override
                                    public void onsendJson(String json) {
                                        Gson gson = new Gson();
                                        RegisterBean bean = gson.fromJson(json, RegisterBean.class);
                                        if(bean.getStatus()==1){
                                            Toast.makeText(UserSetActivity.this,bean.getInfo(),Toast.LENGTH_SHORT).show();
                                            dialog2.dismiss();
                                        }else if (bean.getStatus()==2){
                                            Toast.makeText(UserSetActivity.this,bean.getInfo(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(UserSetActivity.this,"密码输入不一致",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(UserSetActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.ll_file:
                Dialog dialog3 = new Dialog(this,R.style.Dialog_Fullscreen);
                dialog3.setContentView(R.layout.dialog_user_info);
                dialog3.show();
                break;
            case R.id.user_logout:
                startActivity(new Intent(this,LoginActivity.class));
                this.finish();
        }
    }
    public void Click(Dialog dialog){
        TextView selectBtn2  = (TextView) dialog.getWindow().findViewById(R.id.tv_camera);
        TextView selectBtn1  = (TextView) dialog.getWindow().findViewById(R.id.tv_photo);
        TextView close = (TextView) dialog.getWindow().findViewById(R.id.tv_close);
        selectBtn1.setOnClickListener(this);
        selectBtn2.setOnClickListener(this);
        close.setOnClickListener(this);
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
        }
    }

    private Uri getImageUri() {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }


}
