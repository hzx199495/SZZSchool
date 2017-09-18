package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.multidex.MultiDexApplication;

import com.easefun.polyvsdk.PolyvSDKClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.shizhanzhe.szzschool.MainActivity;
import com.shizhanzhe.szzschool.R;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.LinkedList;
import java.util.List;


import cn.jpush.android.api.JPushInterface;

/**
 * Created by hasee on 2016/10/31.
 */
public class MyApplication extends MultiDexApplication {
    public static String proimg = "";
    public static String videotitle = "";
    public static String videototal = "";

    public static int schedule=0;
    public static String videojson;
    public static int videotype; //分类tab position
    public static String videotypeid;//分类ID
    public static String  videoitemid;//视频ID
    public static String tuanid;
    public static String txId;
    public static String videosuggest="";
    public static int position;
    public static DisplayImageOptions displayoptions;

    public static boolean isLogin=false;

    private CrashHandler crashHandler = null;

    public MyApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);

        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);



        crashHandler = new CrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
        Beta.canShowUpgradeActs.add(MainActivity.class);
        Beta.canShowUpgradeActs.add(SZActivity.class);
        Bugly.init(getApplicationContext(), "23d54cd6f4", false);

        displayoptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.img_load) // 设置图片在下载期间显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();// 构建完成


        // 创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

        initPolyvCilent();


    }

    //加密秘钥和加密向量，在后台->设置->API接口中获取，用于解密SDK加密串
    //值修改请参考https://github.com/easefun/polyv-android-sdk-demo/wiki/10.%E5%85%B3%E4%BA%8E-SDK%E5%8A%A0%E5%AF%86%E4%B8%B2-%E4%B8%8E-%E7%94%A8%E6%88%B7%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E5%8A%A0%E5%AF%86%E4%BC%A0%E8%BE%93
    private String iv = "2u9gDPKdX6GyQJKU";
    private String aeskey = "VXtlHmwfS2oYm0CZ";
    public void initPolyvCilent() {

        PolyvSDKClient client = PolyvSDKClient.getInstance();
        //使用SDK加密串来配置
        client.setConfig("juWXL+94L7eUgx+SszWhtlu+0lOHCHgLMhfBlcFmKwWyadH2pxiY3cgzRSVoIA+ltZ8xakNFiEXidWfcJBgT7gjeTz4vDtTMou3G0xOQYaIPy3f1Z1vvwrMgRJYMb0uU+8psOdOmov0UwLyULdGKCQ==", aeskey, iv, getApplicationContext());
        //初始化SDK设置
        client.initSetting(getApplicationContext());

        client.initCrashReport(getApplicationContext());
    }


    private static MyApplication instance;
    private List<Activity> activityList = new LinkedList();

    //单例模式中获取唯一的ExitApplication实例
    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
    //遍历所有Activity并finish

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }

    class CrashHandler implements Thread.UncaughtExceptionHandler {

        private Application app = null;

        public CrashHandler(Application app) {
            this.app = app;
        }

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            Intent intent = new Intent(app, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            app.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}



