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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.CollectBean;
import com.shizhanzhe.szzschool.Bean.CollectListBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.ForumActivity;
import com.shizhanzhe.szzschool.activity.MessageActivity;
import com.shizhanzhe.szzschool.activity.CollectActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.SZActivity;
import com.shizhanzhe.szzschool.activity.UserSetActivity;
import com.shizhanzhe.szzschool.activity.UserZHActivity;
import com.shizhanzhe.szzschool.db.DatabaseOpenHelper;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.w3c.dom.Text;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
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
import java.util.ArrayList;
import java.util.List;

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
    @ViewInject(R.id.user_sz)
    TextView user_sz;
    @ViewInject(R.id.sq)
    TextView sq;
    private Bitmap bitmapbg;
    DbManager manager = DatabaseOpenHelper.getInstance();
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
        final String username = bundle.getString("username");

//        try {
//            manager.delete(CollectBean.class);
//        } catch (DbException e) {
//
//        }
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.COLLECTLIST(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {

                JsonParser parser = new JsonParser();

                JsonArray jsonArray = parser.parse(json).getAsJsonArray();

                Gson gson = new Gson();
                ArrayList<List<CollectListBean.SysinfoBean>> sysinfo = new ArrayList<>();
                ArrayList<String> listId = new ArrayList<>();

                //加强for循环遍历JsonArray
                for (JsonElement pro : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    CollectListBean Bean = gson.fromJson(pro, CollectListBean.class);
                    sysinfo.add(Bean.getSysinfo());
                    listId.add(Bean.getId());
                }


                for (int i = 0; i < listId.size(); i++) {
                    try {
                        manager.save(new CollectBean(listId.get(i), sysinfo.get(i).get(0).getId(), sysinfo.get(i).get(0).getStitle(), sysinfo.get(i).get(0).getThumb(), sysinfo.get(i).get(0).getIntroduce()));
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
            });


         bitmapbg = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.user_bg);

         topbg.setImageBitmap(blurBitmap(bitmapbg));


        ImageLoader.getInstance().displayImage(img,mImageHeader);
        user_name.setText(username);
        user_zh.setOnClickListener(this);
        user_sc.setOnClickListener(this);

        user_sz.setOnClickListener(this);
        sq.setOnClickListener(this);
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

//    //下载网络图片
//    public Bitmap getLocalOrNetBitmap(String url) {
//        Bitmap bitmap = null;
//        InputStream in = null;
//        BufferedOutputStream out = null;
//        try {
//            in = new BufferedInputStream(new URL(url).openStream(), 1024);
//            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
//            out = new BufferedOutputStream(dataStream, 1024);
//            byte[] b = new byte[1024];
//            int read;
//            while ((read = in.read(b)) != -1) {
//                out.write(b, 0, read);
//            }
//            out.flush();
//            byte[] data = dataStream.toByteArray();
//            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//            data = null;
//
//            return bitmap;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_zh:
                startActivity(new Intent(getActivity(), UserZHActivity.class));
                break;
            case R.id.user_sc:
                startActivity(new Intent(getActivity(), CollectActivity.class));
                break;
            case R.id.user_sz:
                startActivity(new Intent(getActivity(), SZActivity.class));
                break;
            case R.id.sq:
                startActivity(new Intent(getActivity(), ForumActivity.class));
        }
    }
}
