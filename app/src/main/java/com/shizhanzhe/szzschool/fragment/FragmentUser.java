package com.shizhanzhe.szzschool.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MessageActivity;
import com.shizhanzhe.szzschool.activity.SCActivity;
import com.shizhanzhe.szzschool.activity.SZActivity;
import com.shizhanzhe.szzschool.activity.UserSetActivity;
import com.shizhanzhe.szzschool.activity.UserZHActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hasee on 2016/11/4.
 */
@ContentView(R.layout.fragment_user)
public class FragmentUser extends Fragment implements View.OnClickListener {
    @ViewInject(R.id.bg)
    ImageView topbg;
    @ViewInject(R.id.setuser)
    FrameLayout setuser;
    @ViewInject(R.id.cv)
    CircleImageView mImageHeader;
    @ViewInject(R.id.tv_name)
    TextView user_name;
    @ViewInject(R.id.user_zh)
    TextView user_zh;
    @ViewInject(R.id.user_sc)
    TextView user_sc;
    @ViewInject(R.id.user_meg)
    TextView user_meg;
    @ViewInject(R.id.user_sz)
    TextView user_sz;
    private Bitmap bitmapbg;
    private Bitmap bitmapcv;

    public static FragmentUser newInstance(String username, String img) {

        Bundle args = new Bundle();
        args.putString("username", username);
        args.putString("img", img);
        FragmentUser fragment = new FragmentUser();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        final String img = bundle.getString("img");
        String username = bundle.getString("username");
        user_name.setText(username);
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmapbg = getLocalOrNetBitmap("http://image72.360doc.com/DownloadImg/2014/04/2301/40991904_7.jpg");



                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"header.jpg");
//                    if(file.exists())
//                    {
////                        bitmapcv=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"header.jpg");
//                    }else{
                        bitmapcv=getLocalOrNetBitmap(img);
//                    }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        topbg.setImageBitmap(blurBitmap(bitmapbg));
                        mImageHeader.setImageBitmap(bitmapcv);
                    }
                });

            }
        }).start();
        setuser.setOnClickListener(this);
        user_zh.setOnClickListener(this);
        user_sc.setOnClickListener(this);
        user_meg.setOnClickListener(this);
        user_sz.setOnClickListener(this);
    }

    //模糊效果
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap blurBitmap(Bitmap bitmap) {

        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        RenderScript rs = RenderScript.create(getActivity());

        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        blurScript.setRadius(25.f);

        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        allOut.copyTo(outBitmap);

        bitmap.recycle();

        rs.destroy();

        return outBitmap;

    }

    //下载网络图片
    public Bitmap getLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);
            byte[] b = new byte[1024];
            int read;
            while ((read = in.read(b)) != -1) {
                out.write(b, 0, read);
            }
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setuser:
                startActivity(new Intent(getActivity(), UserSetActivity.class));
                break;
            case R.id.user_zh:
                startActivity(new Intent(getActivity(), UserZHActivity.class));
                break;
            case R.id.user_sc:
                startActivity(new Intent(getActivity(), SCActivity.class));
                break;
            case R.id.user_meg:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.user_sz:
                startActivity(new Intent(getActivity(), SZActivity.class));
                break;
        }
    }
}
