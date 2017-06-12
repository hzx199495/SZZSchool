package com.shizhanzhe.szzschool.update;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.shizhanzhe.szzschool.Bean.UpdateBean;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.db.DatabaseOpenHelper;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

/**
 * Created by hasee on 2017/1/6.
 */

public class UpdateManager {
    private Context mContext;
    DbManager manager = DatabaseOpenHelper.getInstance();
    double version;
    public UpdateManager(Context context) {
        this.mContext = context;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate(final boolean isToast) {
        /**
         * 在这里请求后台接口，获取更新的内容和最新的版本号
         */
        OkHttpDownloadJsonUtil.downloadJson(mContext, "http://ji.fadangou.com/index1.html", new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                try {
                    double myversion = manager.findFirst(UpdateBean.class).getVersion();// 当前的版本号

                    version =Double.parseDouble(json);// 服务器版本号
                    // 版本的更新信息
                    String version_info = "更新内容\n" + "    1. 更新版本"+version;
                    if (myversion < version) {
                        // 显示提示对话
                        showNoticeDialog(version_info);
                    } else {
                        if (isToast) {
                            Toast.makeText(mContext, "已经是最新版本", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     * 显示更新对话框
     *
     * @param version_info
     */
    private void showNoticeDialog(String version_info) {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("新版本发布");
        builder.setMessage(version_info);
        // 更新
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    MyApplication.version=version;
//                    UpdateBean first = manager.findFirst(UpdateBean.class);
//                    first.setVersion(version);
//                    manager.update(first,"Version");
                    dialog.dismiss();
                    mContext.startService(new Intent(mContext, DownLoadService.class));

            }
        });
        // 稍后更新
        builder.setNegativeButton("以后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }
}
