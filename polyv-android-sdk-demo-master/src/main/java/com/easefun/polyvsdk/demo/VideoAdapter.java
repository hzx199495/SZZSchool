package com.easefun.polyvsdk.demo;

import java.util.List;

import com.easefun.polyvsdk.BitRateEnum;
import com.easefun.polyvsdk.PolyvDownloadProgressListener;
import com.easefun.polyvsdk.PolyvDownloader;
import com.easefun.polyvsdk.PolyvDownloaderErrorReason;
import com.easefun.polyvsdk.PolyvDownloaderManager;
import com.easefun.polyvsdk.R;
import com.easefun.polyvsdk.RestVO;
import com.easefun.polyvsdk.Video;
import com.easefun.polyvsdk.demo.download.PolyvDBservice;
import com.easefun.polyvsdk.demo.download.PolyvDLNotificationService;
import com.easefun.polyvsdk.demo.download.PolyvDLNotificationService.BindListener;
import com.easefun.polyvsdk.demo.download.PolyvDownloadInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class VideoAdapter extends BaseAdapter {
	private List<RestVO> videos;
	private Context context;
	private LayoutInflater inflater;
	private ViewHolder holder;
	private DisplayImageOptions options;
	private PolyvDBservice service;
	private PolyvDLNotificationService downloadService;
	private ServiceConnection serconn;
	private Video.HlsSpeedType hlsSpeedType = Video.HlsSpeedType.SPEED_1X;

	public ServiceConnection getSerConn() {
		return serconn;
	}

	public VideoAdapter(Context context, List<RestVO> videos) {
		serconn = PolyvDLNotificationService.bindDownloadService(context, new BindListener() {

			@Override
			public void bindSuccess(PolyvDLNotificationService downloadService) {
				VideoAdapter.this.downloadService = downloadService;
			}
		});
		this.context = context;
		this.videos = videos;
		this.inflater = LayoutInflater.from(context);
		this.service = new PolyvDBservice(context);

		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.bg_loading) // 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.bg_loading)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.bg_loading) // 设置图片加载/解码过程中错误时候显示的图片
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
				.displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();// 构建完成
	}

	@Override
	public int getCount() {
		if (videos == null)
			return 0;
		return videos.size();
	}

	@Override
	public Object getItem(int position) {
		return videos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.view_video, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.imageview);
			holder.video_title = (TextView) convertView.findViewById(R.id.video_title);
			holder.video_duration = (TextView) convertView.findViewById(R.id.video_duration);
			holder.btn_download = (Button) convertView.findViewById(R.id.btn_download);
			holder.btn_play = (Button) convertView.findViewById(R.id.btn_play);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		RestVO restVO = videos.get(position);
		holder.video_title.setText(restVO.getTitle());
		holder.video_duration.setText(restVO.getDuration());
		holder.btn_download.setOnClickListener(new DownloadListener(restVO.getVid(), restVO.getTitle()));
		holder.btn_play.setOnClickListener(new PlayListener(restVO.getVid()));
		ImageLoader imageloader = ImageLoader.getInstance();
		imageloader.displayImage(restVO.getFirstImage(), holder.image, options, new AnimateFirstDisplayListener());
		return convertView;
	}

	class ViewHolder {
		ImageView image;
		TextView video_title;
		TextView video_duration;
		Button btn_play, btn_download;
	}

	class PlayListener implements View.OnClickListener {
		private String vid;

		public PlayListener(String vid) {
			this.vid = vid;
		}

		@Override
		public void onClick(View arg0) {
			IjkVideoActicity.intentTo(context, 4, 1, "d9a628711ed9512ab7f1d6ec23e137e1_d",
					false);
		}
	}

	class DownloadListener implements View.OnClickListener {
		private String vid;
		private String title;

		public DownloadListener(String vid, String title) {
			this.vid = vid;
			this.title = title;
		}

		@Override
		public void onClick(View v) {
			Video.loadVideo(vid, new Video.OnVideoLoaded() {
				public void onloaded(final Video v) {
					if (v == null) {
						return;
					}

					// 码率数
					String[] items = BitRateEnum.getBitRateNameArray(v.getDfNum());
					String[] speeds = null;
					speeds = new String[] { "1倍速", "1.5倍速" };
					// 选择倍速的view
					RelativeLayout rl = new RelativeLayout(context);
					TextView textView = new TextView(context);
					textView.setText("请选择下载码率");
					textView.setTextSize(20);
					textView.setTextColor(Color.WHITE);
					RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					lp1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
					lp1.addRule(RelativeLayout.CENTER_IN_PARENT);
					textView.setLayoutParams(lp1);
					rl.addView(textView);
					Spinner spinner = new Spinner(context);
					spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
							hlsSpeedType = Video.HlsSpeedType.values()[position];
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
						}
					});
					ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,
							android.R.layout.simple_spinner_item, speeds);
					spinner.setAdapter(arrayAdapter);
					RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
					spinner.setLayoutParams(lp2);
					rl.addView(spinner);
					// 数字2代表的是数组的下标
					final Builder selectDialog = new AlertDialog.Builder(context).setSingleChoiceItems(items, 0,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									int bitrate = which + 1;

									final PolyvDownloadInfo downloadInfo = new PolyvDownloadInfo(vid, v.getDuration(),
											v.getFileSizeMatchVideoType(bitrate), bitrate);
									downloadInfo.setTitle(title);
									downloadInfo.setSpeed(hlsSpeedType.getName());
									Log.i("videoAdapter", downloadInfo.toString());
									if (service != null && !service.isAdd(downloadInfo)) {
										service.addDownloadFile(downloadInfo);
										final int id = PolyvDLNotificationService.getId(v.getVid(), bitrate,
												hlsSpeedType.getName());
										PolyvDownloader polyvDownloader = PolyvDownloaderManager.getPolyvDownloader(vid,
												bitrate, hlsSpeedType);
										polyvDownloader
												.setPolyvDownloadProressListener(new PolyvDownloadProgressListener() {
													private long total;

													@Override
													public void onDownloadSuccess() {
														service.updatePercent(downloadInfo, total, total);
														if (downloadService != null)
															downloadService.updateFinishNF(id);
													}

													@Override
													public void onDownloadFail(PolyvDownloaderErrorReason errorReason) {
														if (downloadService != null)
															downloadService.updateErrorNF(id, false);
													}

													@Override
													public void onDownload(long current, long total) {
														this.total = total;
														if (downloadService != null)
															downloadService.updateDownloadingNF(id,
																	(int) (current * 100 / total), false);
													}
												});
										// 先执行
										if (downloadService != null)
											downloadService.updateStartNF(id, vid, bitrate, hlsSpeedType.getName(),
													title, 0);
										polyvDownloader.start();
									} else {
										((Activity) context).runOnUiThread(new Runnable() {

											@Override
											public void run() {
												Toast.makeText(context, "下载任务已经增加到队列", Toast.LENGTH_SHORT).show();
											}
										});
									}

									dialog.dismiss();
								}
							});
					if (v.getHls15X().size() > 0)
						selectDialog.setCustomTitle(rl);
					else
						selectDialog.setTitle("请选择下载码率");

					selectDialog.show().setCanceledOnTouchOutside(true);
				}
			});
		}
	}
}
