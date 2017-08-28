package com.shizhanzhe.szzschool.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.CollectActivity;
import com.shizhanzhe.szzschool.activity.MyNoteListActivity;
import com.shizhanzhe.szzschool.activity.MyProActivity;
import com.shizhanzhe.szzschool.activity.MyTGActivity;
import com.shizhanzhe.szzschool.activity.SZActivity;
import com.shizhanzhe.szzschool.activity.ScheduleActivity;
import com.shizhanzhe.szzschool.activity.UserSetActivity;
import com.shizhanzhe.szzschool.activity.UserZHActivity;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.shizhanzhe.szzschool.activity.MyApplication.displayoptions;

/**
 * Created by hasee on 2016/11/4.
 */
@ContentView(R.layout.fragment_user)
public class FragmentUser extends Fragment implements View.OnClickListener {


    @ViewInject(R.id.setzl)
    RelativeLayout setzl;
    @ViewInject(R.id.cv)
    CircleImageView mImageHeader;
    @ViewInject(R.id.tv_name)
    TextView user_name;
    @ViewInject(R.id.user_pro)
    RelativeLayout user_pro;
    @ViewInject(R.id.user_zh)
    RelativeLayout user_zh;
    @ViewInject(R.id.user_note)
    RelativeLayout user_note;
    @ViewInject(R.id.user_sc)
    RelativeLayout user_sc;
    @ViewInject(R.id.user_sz)
    RelativeLayout user_sz;
    @ViewInject(R.id.user_tg)
    RelativeLayout user_tg;
    @ViewInject(R.id.user_jd)
    RelativeLayout user_jd;


    @ViewInject(R.id.tv_type)
    TextView tv_type;

    View rootview;

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
        user_zh.setOnClickListener(this);
        user_sc.setOnClickListener(this);
        user_sz.setOnClickListener(this);
        user_tg.setOnClickListener(this);
        user_note.setOnClickListener(this);
        setzl.setOnClickListener(this);
        user_pro.setOnClickListener(this);
        user_jd.setOnClickListener(this);
    }

//    //模糊效果
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    public Bitmap blurBitmap(Bitmap bitmap) {
//        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        RenderScript rs = RenderScript.create(getActivity());
//        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
//        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
//        blurScript.setRadius(25.f);
//        blurScript.setInput(allIn);
//        blurScript.forEach(allOut);
//        allOut.copyTo(outBitmap);
//        bitmap.recycle();
//        rs.destroy();
//        return outBitmap;
//    }


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
            case R.id.setzl:
                startActivity(new Intent(getActivity(), UserSetActivity.class));
                break;
            case R.id.user_note:
                startActivity(new Intent(getActivity(), MyNoteListActivity.class));
                break;
            case R.id.user_pro:
                Intent intent = new Intent(getActivity(), MyProActivity.class);
                intent.putExtra("con", mypro);
                startActivity(intent);
                break;
            case R.id.user_jd:
                startActivity(new Intent(getActivity(), ScheduleActivity.class));
                break;
        }
    }

    String mypro;

    public void getMyProject() {
        SharedPreferences preferences = getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
        String img = preferences.getString("img", "");
        String username = preferences.getString("username", "");
        String vip = preferences.getString("vip", "");
        if (img.contains("http")) {
            ImageLoader.getInstance().displayImage(img, mImageHeader, displayoptions);
        } else {
            ImageLoader.getInstance().displayImage(Path.IMG(img), mImageHeader, displayoptions);
        }

        user_name.setText(username);
        if (vip.equals("1")) {
            tv_type.setText("VIP用户");
            tv_type.setTextColor(Color.RED);
        } else {
            tv_type.setText("普通用户");
            tv_type.setTextColor(Color.WHITE);
        }

    }


//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        scroll.smoothScrollTo(0,20);
//        getMyProject();
//
//    }

    @Override
    public void onResume() {
        super.onResume();
        getMyProject();
        SharedPreferences preferences = getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
        String img = preferences.getString("img", "");
        if (img.contains("http")) {
            ImageLoader.getInstance().displayImage(img, mImageHeader, displayoptions);
        } else {
            ImageLoader.getInstance().displayImage(Path.IMG(img), mImageHeader, displayoptions);
        }
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