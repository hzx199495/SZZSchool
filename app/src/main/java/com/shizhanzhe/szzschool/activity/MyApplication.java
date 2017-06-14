package com.shizhanzhe.szzschool.activity;

import android.graphics.Bitmap;
import android.support.multidex.MultiDexApplication;
import android.util.Log;


import com.easefun.polyvsdk.PolyvDevMountInfo;
import com.easefun.polyvsdk.PolyvSDKClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.shizhanzhe.szzschool.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by hasee on 2016/10/31.
 */
public class MyApplication extends MultiDexApplication {
    public static String zh = "";
    public static String username = "";
    public static String myid = "";
    public static String money = "";
    public static String token = "";
    public static String img = "";
    public static String path = "";
    public static boolean SC = false;
    public static double version;
    public static ImageOptions options;
    public static DisplayImageOptions displayoptions;
    public static final String TAG = MyApplication.class.getSimpleName();
    //加密秘钥和加密向量，在后台->设置->API接口中获取，用于解密SDK加密串
    //值修改请参考https://github.com/easefun/polyv-android-sdk-demo/wiki/10.%E5%85%B3%E4%BA%8E-SDK%E5%8A%A0%E5%AF%86%E4%B8%B2-%E4%B8%8E-%E7%94%A8%E6%88%B7%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E5%8A%A0%E5%AF%86%E4%BC%A0%E8%BE%93
    private String aeskey = "VXtlHmwfS2oYm0CZ";
    /**
     * 加密向量
     */
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


        // 创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

        initPolyvCilent();
    }

    //
    public void initPolyvCilent() {
        //网络方式取得SDK加密串，（推荐）
        //网络获取到的SDK加密串可以保存在本地SharedPreference中，下次先从本地获取
//		new LoadConfigTask().execute();
        PolyvSDKClient client = PolyvSDKClient.getInstance();
        //使用SDK加密串来配置
        client.setConfig("juWXL+94L7eUgx+SszWhtlu+0lOHCHgLMhfBlcFmKwWyadH2pxiY3cgzRSVoIA+ltZ8xakNFiEXidWfcJBgT7gjeTz4vDtTMou3G0xOQYaIPy3f1Z1vvwrMgRJYMb0uU+8psOdOmov0UwLyULdGKCQ==", aeskey, iv, getApplicationContext());
        //初始化SDK设置
        client.initSetting(getApplicationContext());
        //启动Bugly
        client.initCrashReport(getApplicationContext());
        //启动Bugly后，在学员登录时设置学员id
//		client.crashReportSetUserId(userId);
        //获取SD卡信息
        PolyvDevMountInfo.getInstance().init(this, new PolyvDevMountInfo.OnLoadCallback() {

            @Override
            public void callback() {
                if (!PolyvDevMountInfo.getInstance().isSDCardAvaiable()) {
                    // TODO 没有可用的存储设备
                    Log.e(TAG, "没有可用的存储设备");
                    return;
                }

                StringBuilder dirPath = new StringBuilder();
                dirPath.append(PolyvDevMountInfo.getInstance().getSDCardPath()).append(File.separator).append("polyvdownload");
                File saveDir = new File(dirPath.toString());
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }

                //如果生成不了文件夹，可能是外部SD卡需要写入特定目录/storage/sdcard1/Android/data/包名/
                if (!saveDir.exists()) {
                    dirPath.delete(0, dirPath.length());
                    dirPath.append(PolyvDevMountInfo.getInstance().getSDCardPath()).append(File.separator).append("Android").append(File.separator).append("data")
                            .append(File.separator).append(getPackageName()).append(File.separator).append("polyvdownload");
                    saveDir = new File(dirPath.toString());
                    getExternalFilesDir(null); // 生成包名目录
                    saveDir.mkdirs();
                }

                //设置下载存储目录
                PolyvSDKClient.getInstance().setDownloadDir(saveDir);
            }
        });
    }

}



