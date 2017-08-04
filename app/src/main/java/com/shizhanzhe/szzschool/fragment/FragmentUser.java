package com.shizhanzhe.szzschool.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.MyProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.CollectActivity;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.MyNoteListActivity;
import com.shizhanzhe.szzschool.activity.MyTGActivity;
import com.shizhanzhe.szzschool.activity.SZActivity;
import com.shizhanzhe.szzschool.activity.UserSetActivity;
import com.shizhanzhe.szzschool.activity.UserZHActivity;
import com.shizhanzhe.szzschool.adapter.MyProAdapter;
import com.shizhanzhe.szzschool.utils.MyListView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hasee on 2016/11/4.
 */
@ContentView(R.layout.fragment_user)
public class FragmentUser extends Fragment implements View.OnClickListener {
    @ViewInject(R.id.bg)
    ImageView topbg;
    @ViewInject(R.id.cv)
    CircleImageView cv;
    @ViewInject(R.id.zl)
    ImageView zl;
    @ViewInject(R.id.cv)
    CircleImageView mImageHeader;
    @ViewInject(R.id.tv_name)
    TextView user_name;
    @ViewInject(R.id.user_zh)
    TextView user_zh;
    @ViewInject(R.id.user_note)
    TextView user_note;
    @ViewInject(R.id.user_sc)
    TextView user_sc;
    @ViewInject(R.id.user_sz)
    TextView user_sz;
    @ViewInject(R.id.user_tg)
    TextView user_tg;
    @ViewInject(R.id.lv_kc)
    MyListView lv_kc;
    @ViewInject(R.id.nokc)
    LinearLayout nokc;
    @ViewInject(R.id.scroll)
    ScrollView scroll;
    private Bitmap bitmapbg;
    View rootview;
    SVProgressHUD mSVProgressHUD;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatus("加载中...");
        rootview = x.view().inject(this, inflater, null);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSVProgressHUD.show();
        getMyProject();
        user_zh.setOnClickListener(this);
        user_sc.setOnClickListener(this);
        user_sz.setOnClickListener(this);
        user_tg.setOnClickListener(this);
        user_note.setOnClickListener(this);
        cv.setOnClickListener(this);

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_tg:
                startActivity(new Intent(getActivity(), MyTGActivity.class));
                break;
            case R.id.user_zh:
                startActivity(new Intent(getActivity(), UserZHActivity.class));
                break;
            case R.id.user_sc:
                startActivity(new Intent(getActivity(), CollectActivity.class));
                break;
            case R.id.user_sz:
                startActivity(new Intent(getActivity(), SZActivity.class));
                break;
            case R.id.cv:
                startActivity(new Intent(getActivity(), UserSetActivity.class));
                break;
            case R.id.user_note:
                startActivity(new Intent(getActivity(), MyNoteListActivity.class));
        }
    }

    public void getMyProject() {
        SharedPreferences preferences = getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
        String img = preferences.getString("img", "");
        String username = preferences.getString("username", "");
        bitmapbg = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.user_bg);
        topbg.setImageBitmap(blurBitmap(bitmapbg));
        if (img.contains("http")) {
            ImageLoader.getInstance().displayImage(img, mImageHeader);
        } else {
            ImageLoader.getInstance().displayImage(Path.IMG(img), mImageHeader);
        }

        user_name.setText(username);
        OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).MYCLASS(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                final List<MyProBean> list = gson.fromJson(json, new TypeToken<List<MyProBean>>() {
                }.getType());
                MyProAdapter myProAdapter = new MyProAdapter(list, getContext());
                if (list.size() > 0) {
                    nokc.setVisibility(View.GONE);
                    lv_kc.setVisibility(View.VISIBLE);
                    lv_kc.setAdapter(myProAdapter);
                    lv_kc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), DetailActivity.class);
                            String proid = list.get(position).getSysinfo().get(0).getId();
                            intent.putExtra("id", proid);
                            startActivity(intent);
                        }
                    });
                } else {
                    nokc.setVisibility(View.VISIBLE);
                    lv_kc.setVisibility(View.GONE);
                }
                scroll.smoothScrollTo(0, 20);
                mSVProgressHUD.dismiss();
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getMyProject();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
        String img = preferences.getString("img", "");
        if (img.contains("http")) {
            ImageLoader.getInstance().displayImage(img, mImageHeader);
        } else {
            ImageLoader.getInstance().displayImage(Path.IMG(img), mImageHeader);
        }
        scroll.smoothScrollTo(0, 20);
    }
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