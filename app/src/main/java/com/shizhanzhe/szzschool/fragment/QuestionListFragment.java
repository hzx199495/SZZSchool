package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.Bean.QuestionProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.QuestionListActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
public class QuestionListFragment extends android.support.v4.app.Fragment {


    public static String TABLAYOUT_FRAGMENT = "tab_fragment";
    private int protype;
    private int type;
    private String sid;
    private ListView lv;
    ArrayAdapter adapter;
//    StateLayout state_layout;
    List<ProDeatailBean.CiBean> list;
    private QuestionProBean bean;

    public static QuestionListFragment newInstance(int type, QuestionProBean bean) {
        QuestionListFragment fragment = new QuestionListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLAYOUT_FRAGMENT, type);
        bundle.putSerializable("bean", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = (int) getArguments().getSerializable(TABLAYOUT_FRAGMENT);
            bean = (QuestionProBean) getArguments().getSerializable("bean");
            sid = bean.getSid();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_questionlist, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        state_layout = (StateLayout) view.findViewById(R.id.state_layout);
//        state_layout.setTipText(StateLayout.EMPTY, "");
        lv = (ListView) view.findViewById(R.id.lv);
        ImageView iv = view.findViewById(R.id.iv);
        lv.setEmptyView(iv);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.text_item);
        getData();
    }

    protected void initView() {
        switch (type) {
            case 1:
                setData(0);
                break;
            case 2:
                setData(1);
                break;
            case 3:
                setData(2);
                break;
            case 4:
                setData(3);
                break;
            case 5:
                setData(4);
                break;
            case 6:
                setData(5);
                break;
            case 7:
                setData(6);
                break;
            case 8:
                setData(7);
                break;
        }
    }


    void setData(int position) {
//        try {

            final QuestionProBean.ChavideoBean chavideoBean = bean.getChavideo().get(position);
            final List<QuestionProBean.ChavideoBean.VideoBean> video = chavideoBean.getVideo();

                for (QuestionProBean.ChavideoBean.VideoBean VideoBean : video
                        ) {
                    adapter.add(VideoBean.getTitle());
                }
                lv.setAdapter(adapter);
            if (video.size() == 0) {
//                state_layout.showEmptyView();
            }
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        Intent intent = new Intent(getActivity(), QuestionListActivity.class);
                        intent.putExtra("videoId", video.get(position).getId());
                        intent.putExtra("name", video.get(position).getTitle());
                        intent.putExtra("sid", sid);
                        intent.putExtra("pid", chavideoBean.getCid());
                        startActivity(intent);
                    }
                });

//        } catch (Exception e) {
//            return;
//        }
    }

    void getData() {
        if (sid.contains("41")) {
            protype = 1;
        } else {
            protype = 2;
        }
        initView();
    }

}
