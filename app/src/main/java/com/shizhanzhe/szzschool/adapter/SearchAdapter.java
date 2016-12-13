package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.shizhanzhe.szzschool.Bean.SearchBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.ViewHolder;
import com.shizhanzhe.szzschool.widge.CommonAdapter;

import org.xutils.x;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


/**
 * Created by yetwish on 2015-05-11
 */

public class SearchAdapter extends CommonAdapter<SearchBean> {

    public SearchAdapter(Context context, List<SearchBean> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, int position) {

        holder.setText(R.id.item_search_tv_title, mData.get(position).getTitle());

    }


}
