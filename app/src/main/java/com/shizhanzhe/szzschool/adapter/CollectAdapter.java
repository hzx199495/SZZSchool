package com.shizhanzhe.szzschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.CollectListBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
//import com.shizhanzhe.szzschool.video.AnimateFirstDisplayListener;

import java.util.ArrayList;
import java.util.List;

import static com.shizhanzhe.szzschool.activity.MyApplication.displayoptions;

/**
 * Created by hasee on 2016/12/20.
 */

public class CollectAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    ArrayList<List<CollectListBean.SysinfoBean>>  list;
    Context context;
    ArrayList<String> listId;
    public CollectAdapter(Context context, ArrayList<List<CollectListBean.SysinfoBean>>  list,ArrayList<String> listId) {
        this.context=context;
        this.list=list;
        this.listId=listId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.adapter_collect,null);
            holder=new ViewHolder();
            holder.iv= (ImageView) convertView.findViewById(R.id.collect_img);
            holder.title= (TextView) convertView.findViewById(R.id.collect_title);
            holder.del= (TextView) convertView.findViewById(R.id.delcollect);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        final CollectListBean.SysinfoBean bean = list.get(position).get(0);
        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage(Path.IMG(bean.getThumb()), holder.iv, displayoptions);
        holder.title.setText(bean.getStitle());

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    list.remove(position);
                    notifyDataSetChanged();

                    OkHttpDownloadJsonUtil.downloadJson(context, Path.DELCOLLECT(MyApplication.myid, listId.get(position), MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                        @Override
                        public void onsendJson(String json) {
                        }
                    });

            }
        });
//        holder.iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DbManager manager = DatabaseOpenHelper.getInstance();
//                try {
//                    List<SearchBean> intentbean= manager.selector(SearchBean.class).where("proid", "=", bean.getId()).findAll();
//
//                    Intent intent=new Intent(context, DetailActivity.class);
//                    intent.putExtra("id",intentbean.get(0).getProid());
//                    intent.putExtra("img",intentbean.get(0).getImg());
//                    intent.putExtra("title",intentbean.get(0).getTitle());
//                    intent.putExtra("intro",intentbean.get(0).getIntro());
//                    intent.putExtra("price",intentbean.get(0).getPrice());
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                } catch (DbException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        });
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView title;
        TextView del;
    }
}
