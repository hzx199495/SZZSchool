package com.shizhanzhe.szzschool.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhanzhe.szzschool.R;


public class PolyvTalkSendActivity extends Activity {
	// 返回按钮
	private ImageView iv_finish;
	// 发送按钮
	private TextView tv_send;
	// 话题，内容
	private EditText et_msg;

	private void findIdAndNew() {
		iv_finish = (ImageView) findViewById(R.id.iv_finish);
		tv_send = (TextView) findViewById(R.id.tv_send);
		et_msg = (EditText) findViewById(R.id.et_msg);
	}

	private void initView() {
		iv_finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
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
				Intent intent = new Intent();
				Log.i("_______sendMsg",et_msg.getText().toString());

				intent.putExtra("sendMsg", et_msg.getText().toString());
				setResult(12, intent);
				finish();
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.polyv_activity_talk_send);
		findIdAndNew();
		initView();
	}
}
