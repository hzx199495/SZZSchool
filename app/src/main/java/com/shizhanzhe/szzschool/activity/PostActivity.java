package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.Image;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.StatusBarUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
 * Created by hasee on 2017/1/4.
 * 发帖
 */
@ContentView(R.layout.activity_post)
public class PostActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.subject)
    EditText subject;
    @ViewInject(R.id.con)
    EditText con;
    @ViewInject(R.id.postimg)
    TextView postimg;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.post)
    TextView post;
    private static int RESULT_LOAD_IMAGE = 1;
    private String fid;
    private String uid;

    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setStatusBarColor(this,R.color.white); }
        SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
        uid = preferences.getString("uid", "");
        token = preferences.getString("token", "");
        fid = getIntent().getStringExtra("fid");
        postimg.setOnClickListener(this);
        post.setOnClickListener(this);
        con.setMovementMethod(LinkMovementMethod.getInstance()); //点击图片响应
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.postimg:
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case R.id.post:

                String title = subject.getText().toString();
                String content = con.getText().toString();
                if ("".equals(title)&&"".equals(content)){
//                    dialog = new QMUITipDialog.Builder(this).setIconType(4).setTipWord("标题与内容不能为空").create();
//                    dialog.show();
//                    mhandler.sendEmptyMessageDelayed(1,1500);
                    Toast.makeText(this, "标题与内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("con", content).add("subject", title)
                        .build();
                //在构建Request对象时，调用post方法，传入RequestBody对象
                Request request = new Request.Builder()
                        .url("https://shizhanzhe.com/index.php?m=pcdata.fabu_tiezi&pc=1&uid=" + uid + "&fid=" + fid + "&token=" + token)
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        str=response.body().string();
                        mHandler.sendEmptyMessage(1);
                    }
                });
                break;
            case R.id.back:
                finish();
        }
    }

    private String str="";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1) {

                if (str.contains("成功")){
                    Toast.makeText(PostActivity.this, str, Toast.LENGTH_SHORT).show();
//                    dialog = new QMUITipDialog.Builder(PostActivity.this).setIconType(4).setTipWord(str).create();
//                    dialog.show();
//                    mhandler.sendEmptyMessageDelayed(1,1500);
                }
            }else if (msg.what==2){
                showGalleryPhoto(data);
            }else if (msg.what==3){
                Toast.makeText(PostActivity.this, "图片大小不超过100KB", Toast.LENGTH_SHORT).show();
//                dialog = new QMUITipDialog.Builder(PostActivity.this).setIconType(4).setTipWord("图片大小不超过100KB").create();
//                dialog.show();
//                mhandler.sendEmptyMessageDelayed(1,1500);
            }
        }
    };
    private Intent data;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            this.data=data;
            try {
                OkHttpClient client = new OkHttpClient();
                Bitmap bm=BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("imgFile","1.jpg", RequestBody.create(MediaType.parse("image/jpeg"),byteArrayOutputStream.toByteArray()))
                        ;
                MultipartBody build = builder.build();

                Request bi = new Request.Builder()
                        .url("https://shizhanzhe.com/index.php?m=pcdata.uploadimg&pc=1&uid=" + uid + "&token=" + token+"&dir=image")
                        .post(build)
                        .build();

                client.newCall(bi).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("____resultonFailure",e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        Log.e("____resultonResponse",s);
                        Gson gson = new Gson();
                        Image image = gson.fromJson(s, Image.class);
                        if (image.getUrl()==null){
                            mHandler.sendEmptyMessage(3);
                        }else {
                            url = image.getUrl();
                            mHandler.sendEmptyMessage(2);
                        }

                    }
                });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }




        }

    }
    private String url;
    private String encode(String path) {
        //decode to bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        //convert to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        //base64 encode
        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        String encodeString = new String(encode);
        return encodeString;
    }

    /**
     * 将图片转成可在EditView显示的CharSequence
     *
     * @param picPath 需要显示的图片路径
     * @return
     */
    private CharSequence getDrawableStr(String picPath) {
        InputStream is;
        try {
            String str = "<img src=\"" + url + "\"/>";
            is = new FileInputStream(picPath);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inTempStorage = new byte[100 * 1024];
            opts.inPreferredConfig = Bitmap.Config.RGB_565; // 默认是Bitmap.Config.ARGB_8888
            opts.inSampleSize = 4;
            /* 下面两个字段需要组合使用 ，说是为了节约内存 */
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            Bitmap bm = BitmapFactory.decodeStream(is, null, opts);

            final SpannableString ss = new SpannableString(str);
            // 定义插入图片
            Drawable drawable = new BitmapDrawable(bm);
            drawable.setBounds(2, 0, 400, 350);
            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            ss.setSpan(span, 0, ss.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            return ss;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    private void showGalleryPhoto(Intent data) {
        if (data == null) {
            return;
        }
        Uri photoUri = data.getData();
        if (photoUri == null) {
            return;
        }

        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
        String picPath = null;
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
//			cursor.close();   android.database.StaleDataException: Attempted to access a cursor after it has been closed.

        }

        if (!TextUtils.isEmpty(picPath)) {
            con.append(getDrawableStr(picPath));
        }
    }

    public static final String IMG_START = "<img src=\"";
    public static final String IMG_END = "\"/>";
    /**
     * 从content解析出图片标签<img>，并设置到EditText中。
     * 算法：假设 aaaaa, <img src="/mnt/sdcard/xxx.png"/> bbbbbbbb————<img src="/mnt/sdcard/yyyy.png/>dddddddd
     * 改方法将“aaa”到第一个“/>”为止作为一个处理单元，然后再处理“bbbb”，到第二个“/>”，这两个步骤以及接下去的的处理方式其实是一样的，所以我用了递归。
     *
     * @param etContent
     * @param content
     */
    String conend = "无内容";

    private void updateContent(String content) {
        //递归出口
        if (TextUtils.isEmpty(content)) {
            return;
        }
        Log.i("内容", " == " + content);

        int startIndex = 0, endIndex = 0;
        int imgStartIndex = content.indexOf(IMG_START);
        if (imgStartIndex < 0) {//没有<img>标签，说明没有图片了
            endIndex = content.length();
        } else {
            endIndex = imgStartIndex;
        }

        String str1 = content.substring(startIndex, endIndex);
        Log.i("str1", " == " + str1);

//        etContent.append(str);
        content = content.substring(endIndex, content.length());//将变量str表示的字符串删除
        Log.i("将变量str表示的字符串删除", " == " + content);

        //设置 img
        if (TextUtils.isEmpty(content)) {
            return;
        }

        int imgEndIndex = content.indexOf(IMG_END);
        String str2 = content.substring(IMG_START.length(), imgEndIndex);
        Log.i("path_", str2);
        String img = encode(str2);
//        Log.e("-", "String2 == " + str);
//        etContent.append(getDrawableStr(str));


        String str3 = content.substring(imgEndIndex + IMG_END.length(), content.length());//将变量str表示的字符串删除
        content = str3;
        Log.i("str3", " == " + str3);
        conend = str1 + img
                + str3;
        Log.i("字符串", conend);
        Log.e("content", content);
        updateContent(content);

    }

}











