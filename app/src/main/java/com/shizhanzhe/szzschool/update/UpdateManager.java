package com.shizhanzhe.szzschool.update;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.shizhanzhe.szzschool.BuildConfig;
import com.shizhanzhe.szzschool.activity.LoginActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.SZActivity;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;

import java.io.File;

import cn.finalteam.okhttpfinal.FileDownloadCallback;
import cn.finalteam.okhttpfinal.HttpRequest;


/**
 * Created by hasee on 2017/welcome/6.
 */

public class UpdateManager {
    private Context mContext;
    public UpdateManager(Context context) {
        this.mContext = context;
    }
    ProgressDialog downdialog;
    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
            "SZZUpdate" + File.separator + "szz.apk");
    /**
     * 检测软件更新
     */
    public void checkUpdate(final boolean isToast) {
        /**
         * 在这里请求后台接口，获取更新的内容和最新的版本号
         */
        OkHttpDownloadJsonUtil.downloadJson(mContext, "https://shizhanzhe.com/pc_zb/index.php?a=1&m=1", new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                int myversion = getVersionCode(mContext);// 当前的版本号
                int nVersion_code = Integer.parseInt(json);
                if (myversion < nVersion_code) {
                    // 显示提示对话
                    showNoticeDialog();
                } else {
                    if (isToast) {
                        Toast.makeText(mContext, "已经是最新版本", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    /**
     * 显示更新对话框
     *
     * @param
     */
    private void showNoticeDialog() {
        downdialog = new ProgressDialog(mContext);
        downdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        downdialog.setTitle("版本更新");
        downdialog.setMessage("更新中,请稍候...");
        downdialog.setMax(100);
        downdialog.setCancelable(false);// 设置是否可以通过点击Back键取消
        downdialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("新版本发布！");
        builder.setMessage("是否开始更新？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                downdialog.show();
                downlod();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        builder.create().show();

    }
    public void downlod() {

        HttpRequest.download("https://shizhanzhe.com/andriod/szz.apk",file, new FileDownloadCallback() {
            //开始下载
            @Override
            public void onStart() {
                super.onStart();
                downdialog.show();
            }

            //下载进度
            @Override
            public void onProgress(int progress, long networkSpeed) {
                super.onProgress(progress, networkSpeed);
                downdialog.setProgress(progress);
            }

            //下载失败
            @Override
            public void onFailure() {
                super.onFailure();
                Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();

            }

            //下载完成（下载成功）
            @Override
            public void onDone() {
                super.onDone();
                Toast.makeText(mContext, "下载成功,开始安装", Toast.LENGTH_SHORT).show();
                installAPK(file);
            }
        });
    }
    //调用系统的安装方法
    private void installAPK(File savedFile) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileprovider", savedFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(savedFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mContext.startActivity(intent);
    }
    /**
     * @return 当前应用的版本号
     */
    public int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
