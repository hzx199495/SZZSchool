package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shizhanzhe.szzschool.R;

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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hasee on 2017/1/4.
 */
@ContentView(R.layout.activity_post)
public class PostActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.subject)
    EditText subject;
    @ViewInject(R.id.con)
    EditText con;
    @ViewInject(R.id.postimg)
    TextView postimg;
    @ViewInject(R.id.post)
    TextView post;
    private static int RESULT_LOAD_IMAGE = 1;
    String fid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
         fid = getIntent().getStringExtra("fid");
        postimg.setOnClickListener(this);
        post.setOnClickListener(this);

        con.setMovementMethod(LinkMovementMethod.getInstance()); //点击图片能不能有反应这句很关键
//        updateContent(con, mContentStr);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.postimg:
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case R.id.post:
                String title=subject.getText().toString();
                String content = con.getText().toString();
                updateContent(content);
                OkHttpClient client = new OkHttpClient();
                RequestBody body=new FormBody.Builder()
                        .add("con",conend).add("subject",title)
                        .build();
                 //在构建Request对象时，调用post方法，传入RequestBody对象
                Request request=new Request.Builder()
                        .url("http://shizhanzhe.com/index.php?m=pcdata.fabu_tiezi&pc=1&uid="+MyApplication.myid+"&fid="+fid+"&token="+MyApplication.token)
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Toast.makeText(getApplicationContext(),"发表成功",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            showGalleryPhoto(data);
        }
    }
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
            String str = "<img src=\"" + picPath + "\"/>";
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

        String[] pojo = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
        String picPath = null;
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
//			cursor.close();   android.database.StaleDataException: Attempted to access a cursor after it has been closed.

        }

        if(!TextUtils.isEmpty(picPath)) {
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
    String conend="无内容";
    private void updateContent(String content) {
        //递归出口
        if(TextUtils.isEmpty(content)) {
            return ;
        }
        Log.i("内容", " == " + content);

        int startIndex = 0, endIndex = 0;
        int imgStartIndex = content.indexOf(IMG_START);
        if(imgStartIndex < 0) {//没有<img>标签，说明没有图片了
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
        if(TextUtils.isEmpty(content)) {
            return;
        }

        int imgEndIndex = content.indexOf(IMG_END);
        String str2 = content.substring(IMG_START.length(), imgEndIndex);
        Log.i("path_",str2);
        String img=encode(str2);
//        Log.e("-", "String2 == " + str);
//        etContent.append(getDrawableStr(str));


        String str3 = content.substring(imgEndIndex+IMG_END.length(), content.length());//将变量str表示的字符串删除
        content=str3;
        Log.i("str3", " == " + str3);
         conend=str1+img
                 +str3;
        Log.i("字符串",conend);
        Log.e("content",content);
        updateContent(content);

    }
}











