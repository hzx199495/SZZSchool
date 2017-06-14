package com.shizhanzhe.szzschool.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.MyProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.CollectActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.MyTGActivity;
import com.shizhanzhe.szzschool.activity.SZActivity;
import com.shizhanzhe.szzschool.activity.UserSetActivity;
import com.shizhanzhe.szzschool.activity.UserZHActivity;
import com.shizhanzhe.szzschool.adapter.MyProAdapter;
import com.shizhanzhe.szzschool.utils.MyGridView;
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
    @ViewInject(R.id.setuser)
    FrameLayout setuser;
    @ViewInject(R.id.zl)
    ImageView zl;
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
    @ViewInject(R.id.user_tg)
    TextView user_tg;
    @ViewInject(R.id.lv_kc)
    MyGridView lv_kc;
    @ViewInject(R.id.nokc)
    LinearLayout nokc;

    private Bitmap bitmapbg;
    View rootview;

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
        rootview = x.view().inject(this, inflater, null);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMyProject();
        Bundle bundle = getArguments();
        final String img = bundle.getString("img");
        final String username = bundle.getString("username");
        bitmapbg = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.user_bg);
        topbg.setImageBitmap(blurBitmap(bitmapbg));
        ImageLoader.getInstance().displayImage(img, mImageHeader);
        user_name.setText(username);
        user_zh.setOnClickListener(this);
        user_sc.setOnClickListener(this);
        user_sz.setOnClickListener(this);
        user_tg.setOnClickListener(this);
        zl.setOnClickListener(this);
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
            case R.id.zl:
                startActivity(new Intent(getActivity(), UserSetActivity.class));
                break;
        }
    }
    public void getMyProject(){
        OkHttpDownloadJsonUtil.downloadJson(getContext(), Path.MYCLASS(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
//                List<MyProBean.SysinfoBean> sysinfo = gson.fromJson(json, MyProBean.class).getSysinfo();
//                MyProAdapter myProAdapter = new MyProAdapter(sysinfo, getContext());
//                nokc.setVisibility(View.GONE);
//                lv_kc.setVisibility(View.VISIBLE);
//                lv_kc.setAdapter(myProAdapter);
            }
        });
    }
//    public void getTG() {
//        OkHttpDownloadJsonUtil.downloadJson(getContext(), Path.MYKT(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
//            @Override
//            public void onsendJson(String json) {
//                Gson gson = new Gson();
//                final List<MyKTBean> list = gson.fromJson(json, new TypeToken<List<MyKTBean>>() {
//                }.getType());
//                if (list != null && list.size() > 0) {
//                    lv_kc.setVisibility(View.VISIBLE);
//                    nokc.setVisibility(View.GONE);
//                    lv_kc.setAdapter(new MyKTAdapter(getContext(), list));
//                    lv_kc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            try {
//                                List<TGsqlBean> proid = manager.selector(TGsqlBean.class).where("tuanid", "=", list.get(position).getTuanid()).findAll();
//                                Intent intent = new Intent();
//                                intent.setClass(getContext(), TGDetailActivity.class);
//                                intent.putExtra("title", proid.get(0).getTitle());
//                                intent.putExtra("img", proid.get(0).getImg());
//                                intent.putExtra("time", proid.get(0).getTime());
//                                intent.putExtra("intro", proid.get(0).getIntro());
//                                intent.putExtra("yjprice", proid.get(0).getYjprice());
//                                intent.putExtra("id", proid.get(0).getId());
//                                intent.putExtra("tuanid", proid.get(0).getTuanid());
//                                intent.putExtra("price", "100");
//                                intent.putExtra("type", 1);
//                                getContext().startActivity(intent);
//                            } catch (DbException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    });
//                }
//
//
//            }
//
//        });
//    }


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