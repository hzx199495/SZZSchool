package com.shizhanzhe.szzschool.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.ForumTalkEdittextActivity;


public class PolyvTalkSendActivity extends Activity {
	// 发送按钮
	private TextView tv_send;
	// 话题，内容
	private EditText et_msg;
	private void findIdAndNew() {
		et_msg = (EditText) findViewById(R.id.et_talk);
		tv_send = (TextView) findViewById(R.id.tv_send);
	}

	private void initView() {
		et_msg.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().trim().length() > 0 && et_msg.getText().toString().trim().length() > 0) {
					tv_send.setEnabled(true);
					tv_send.setTextColor(getResources().getColorStateList(R.color.polyv_send_text_color));
				} else {
					tv_send.setEnabled(false);
					tv_send.setTextColor(getResources().getColor(R.color.top_right_text_color_gray));
				}
			}
		});
		tv_send.setEnabled(false);
		tv_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (et_msg.getText().toString().trim().length()==0) {
					Toast.makeText(PolyvTalkSendActivity.this,"发送信息不能为空!",Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("sendMsg", et_msg.getText().toString());
				setResult(12, intent);
				finish();
			}
		});
	}
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0,0);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.polyv_activity_talk_edittext);
		findIdAndNew();
		initView();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP)
			finish();
		return true;
	}
}
