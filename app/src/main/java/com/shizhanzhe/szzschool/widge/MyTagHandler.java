package com.shizhanzhe.szzschool.widge;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;

import org.xml.sax.XMLReader;

import java.io.File;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyTagHandler implements TagHandler {

	private Context context;
	
	public MyTagHandler(Context context) {
		this.context = context;
	}
	
	@Override
	public void handleTag(boolean opening, String tag, Editable output,
						  XMLReader xmlReader) {
		// TODO Auto-generated method stub

		// 处理标签<img>
		if (tag.toLowerCase().equals("img")) {
			// 获取长度
			int len = output.length();
			// 获取图片地址
			ImageSpan[] images = output.getSpans(len-1, len, ImageSpan.class);
			String path="";
			if (images[0].getSource().contains("http")){
				path=images[0].getSource();
			}else{
				path="https://www.shizhanzhe.com"+images[0].getSource();
			}
			String imgURL = path;
			Log.i("imgURL",imgURL);

			// 使图片可点击并监听点击事件
			output.setSpan(new ImageClick(context, imgURL), len-1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
	}

	private class ImageClick extends ClickableSpan {

		private String url;
		private Context context;

		public ImageClick(Context context, String url) {
			this.context = context;
			this.url = url;
		}

		@Override
		public void onClick(View widget) {

			Log.i("点击图片","");
			// 将图片URL转化为本地路径，可以将图片处理类里的图片处理过程写为一个方法，方便调用

			String imageName = Common.md5(url);
			//获取图片后缀名
			String[] ss = url.split("\\.");
			String ext = ss[ss.length - 1];

			// 最终图片保持的地址
			String savePath = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/test/" + imageName + "." + ext;
			File file = new File(savePath);
			Uri uri = FileProvider.getUriForFile(context, "com.shizhanzhe.szzschool.fileProvider", file);

			if (file.exists()) {
				// 处理点击事件，开启一个新的activity来处理显示图片
				Intent intent = new Intent();
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
					intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
					intent.setAction(Intent.ACTION_VIEW);
					intent.setDataAndType(uri, "image/*");
					intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
				}else {
					intent.setAction(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file), "image/*");
					intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
				}
				context.startActivity(intent);

			}
		}
		
	}

}
