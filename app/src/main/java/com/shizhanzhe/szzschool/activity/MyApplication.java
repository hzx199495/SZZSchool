package com.shizhanzhe.szzschool.activity;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.easefun.polyvsdk.PolyvSDKClient;
import com.easefun.polyvsdk.SDKUtil;
import com.easefun.polyvsdk.server.AndroidService;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.shizhanzhe.szzschool.BuildConfig;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.video.PolyvDemoService;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by hasee on 2016/10/31.
 */
public class MyApplication extends Application {
    public static String  zh="";
    public static String  username="";
    public static String myid="";
    public static String money="";
    public static String token="";
    public static String img="";
    public static String path="";
    public static boolean SC=false;
    public static double version;
    public static ImageOptions options;
    public static DisplayImageOptions displayoptions;
    private static final String TAG = MyApplication.class.getSimpleName();
    private ServiceStartErrorBroadcastReceiver serviceStartErrorBroadcastReceiver = null;
    //加密秘钥和加密向量，在后台->设置->API接口中获取，用于解密SDK加密串
    //值修改请参考https://github.com/easefun/polyv-android-sdk-demo/wiki/10.%E5%85%B3%E4%BA%8E-SDK%E5%8A%A0%E5%AF%86%E4%B8%B2-%E4%B8%8E-%E7%94%A8%E6%88%B7%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E5%8A%A0%E5%AF%86%E4%BC%A0%E8%BE%93
    private String aeskey = "VXtlHmwfS2oYm0CZ";
    /** 加密向量 */
    private String iv = "2u9gDPKdX6GyQJKU";


    public MyApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        displayoptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.bg_loading) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher) // 设置图片加载/解码过程中错误时候显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();// 构建完成




        options = new ImageOptions.Builder()
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .build();


        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "polyvSDK/Cache");
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(2)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                // .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                // You can pass your own memory cache implementation/
                .memoryCache(new WeakMemoryCache()).memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                // .discCacheFileNameGenerator(new Md5FileNameGenerator())//
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileCount(100)
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000))
                .writeDebugLogs() // Remove for release app
                .build();

        // Initialize ImageLoader with configuration
        ImageLoader.getInstance().init(config);
        initPolyvCilent();
    }
//
    public void initPolyvCilent() {
        //OPPO手机自动熄屏一段时间后，会启用系统自带的电量优化管理，禁止一切自启动的APP（用户设置的自启动白名单除外）。
        //如果startService异常，就会发送消息上来提醒异常了
        //如不需要额外处理，也可不接收此信息
        IntentFilter statusIntentFilter = new IntentFilter(AndroidService.SERVICE_START_ERROR_BROADCAST_ACTION);
        statusIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        serviceStartErrorBroadcastReceiver = new ServiceStartErrorBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceStartErrorBroadcastReceiver, statusIntentFilter);


        PolyvSDKClient client = PolyvSDKClient.getInstance();
        //设置SDK加密串
//		client.setConfig("你的SDK加密串", aeskey, iv);
        client.setConfig("juWXL+94L7eUgx+SszWhtlu+0lOHCHgLMhfBlcFmKwWyadH2pxiY3cgzRSVoIA+ltZ8xakNFiEXidWfcJBgT7gjeTz4vDtTMou3G0xOQYaIPy3f1Z1vvwrMgRJYMb0uU+8psOdOmov0UwLyULdGKCQ==", aeskey, iv);
        //初始化数据库服务
        client.initDatabaseService(this);
        //启动服务
        client.startService(getApplicationContext(), PolyvDemoService.class);

    }

    private class ServiceStartErrorBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("msg");
            Log.e(TAG, msg);
        }
    }




}
