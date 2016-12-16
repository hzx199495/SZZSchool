package com.shizhanzhe.szzschool.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.shizhanzhe.szzschool.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.myfragment)
public class MyFragment extends Fragment {
	@ViewInject(R.id.fl_gv)
	GridView fl_gv;
	public static final String TAG = "MyFragment";
	private String str;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.myfragment, null);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		//得到数据
//		str = getArguments().getString(TAG);
//		fl_gv
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Bundle bundle = getArguments();
		bundle.getString("");
	}
}
