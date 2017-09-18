package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhanzhe.szzschool.MainActivity;
import com.shizhanzhe.szzschool.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 */
@ContentView(R.layout.activity_welcome_guide)
public class WelcomeGuideActivity extends Activity {
	@ViewInject(R.id.welcome_guide_viewpager)
	ViewPager pager;
	@ViewInject(R.id.welcome_guide_btn)
	ImageView btn;

	private List<View> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		x.view().inject(this);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(WelcomeGuideActivity.this, MainActivity.class));
				finish();
			}
		});
		initViewPager();
	}

	//初始化viewpager
	public void initViewPager()
	{

		list=new ArrayList<View>();
		ImageView iv=new ImageView(this);
		iv.setImageResource(R.drawable.w1);
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		list.add(iv);
		ImageView iv1=new ImageView(this);
		iv1.setImageResource(R.drawable.w2);
		iv1.setScaleType(ImageView.ScaleType.FIT_XY);
		list.add(iv1);
		ImageView iv2=new ImageView(this);
		iv2.setImageResource(R.drawable.w3);
		iv2.setScaleType(ImageView.ScaleType.FIT_XY);
		list.add(iv2);
		pager.setAdapter(new MyViewPagerAdapter());

		//监听滑动效果
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			//被选中的方法
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if(arg0==2)//如果是第三个页面
				{

					btn.setVisibility(View.VISIBLE);

				}else
				{


					btn.setVisibility(View.GONE);

				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
	//自定义viewpager的适配器
	class MyViewPagerAdapter extends PagerAdapter {


		//计算需要多少item显示
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		//初始化item实例方法
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(list.get(position));
			return list.get(position);
		}
		//item的销毁的方法
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			//必须注销掉父类的方法
			//super.destroyItem(container, position, object);
			container.removeView(list.get(position));
		}

	}

}
