package com.shizhanzhe.szzschool.widge;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html.ImageGetter;
import android.widget.TextView;

import com.shizhanzhe.szzschool.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MyImageGetter implements ImageGetter {
	
	private Context context;
	private TextView tv;
	
	public MyImageGetter(Context context, TextView tv) {
		this.context = context;
		this.tv = tv;
	}
	
	@Override
	public Drawable getDrawable(String source) {
		// TODO Auto-generated method stub
		//将source进行MD5加密并保存至本地
		String path="";
		if(source.contains("http")){
			 path=source;
		}else {
			path="http://www.shizhanzhe.com" + source;
		}
		String imageName = Common.md5(path);
		String sdcardPath = Environment.getExternalStorageDirectory().toString(); // 获取SDCARD的路径
		//获取图片后缀名
		String[] ss = source.split("\\.");
		String ext = ss[ss.length - 1];

		// 最终图片保持的地址
		String savePath = sdcardPath + "/" + context.getPackageName() + "/" + imageName + "." + ext;

		File file = new File(savePath);
		if (file.exists()) {
			// 如果文件已经存在，直接返回
			Drawable drawable = Drawable.createFromPath(savePath);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			return drawable;
		}

		// 不存在文件时返回默认图片，并异步加载网络图片
		Resources res = context.getResources();
		URLDrawable drawable = new URLDrawable(res.getDrawable(R.drawable.bg_loading));
		new ImageAsync(drawable).execute(savePath, path);
		return drawable;

	}

	private class ImageAsync extends AsyncTask<String, Integer, Drawable> {

		private URLDrawable drawable;

		public ImageAsync(URLDrawable drawable) {
			this.drawable = drawable;
		}

		@Override
		protected Drawable doInBackground(String... params) {
			// TODO Auto-generated method stub
			String savePath = params[0];
			String url = params[1];

			InputStream in = null;
			try {
				// 获取网络图片
				HttpGet http = new HttpGet(url);
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = (HttpResponse) client.execute(http);
				BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(response.getEntity());
				in = bufferedHttpEntity.getContent();

			} catch (Exception e) {
				try {
					if (in != null)
						in.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

			if (in == null) return drawable;

			try {
				File file = new File(savePath);
				String basePath = file.getParent();
				File basePathFile = new File(basePath);
				if (!basePathFile.exists()) {
					basePathFile.mkdirs();
				}
				file.createNewFile();
				FileOutputStream fileout = new FileOutputStream(file);
				byte[] buffer = new byte[4*1024];
				while (in.read(buffer) != -1) {
					fileout.write(buffer);
				}
				fileout.flush();

				Drawable mDrawable = Drawable.createFromPath(savePath);
				return mDrawable;
			} catch (Exception e) {
				// TODO: handle exception
			}
			return drawable;
		}

		@Override
		protected void onPostExecute(Drawable result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				drawable.setDrawable(result);
				tv.setText(tv.getText()); // 通过这里的重新设置 TextView 的文字来更新UI
			}
		}
		
	}

	public class URLDrawable extends BitmapDrawable {
		
		private Drawable drawable;
		
		public URLDrawable(Drawable defaultDraw) {
			setDrawable(defaultDraw);
		}
		
		private void setDrawable(Drawable nDrawable) {
			drawable = nDrawable;
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		}

		@Override
		public void draw(Canvas canvas) {
			// TODO Auto-generated method stub
			drawable.draw(canvas);
		}
		
	}
}
