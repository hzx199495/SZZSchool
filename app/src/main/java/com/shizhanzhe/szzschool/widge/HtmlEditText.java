package com.shizhanzhe.szzschool.widge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


import org.xutils.common.util.LogUtil;

import java.util.List;

/**
 * 当前类注释: EditText 实现图文混排
 */
public class HtmlEditText extends LinearLayout {

    public interface OnChoosePicListener {
        void onChoose();
    }

    private OnChoosePicListener onChoosePicListener;

    public void setOnChoosePicListener(OnChoosePicListener onChoosePicListener) {
        this.onChoosePicListener = onChoosePicListener;
    }

    public HtmlEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public HtmlEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HtmlEditText(Context context) {
        super(context);
        init(context);
    }

    private Context context;
    private ImageView choose;
    private EditText et;
    private int width;

    private void init(Context context) {
        this.context = context;
//        LayoutInflater.from(context).inflate(R.layout.html_edit, this);
//        et = (EditText) this.findViewById(R.id.et);
//        choose = (ImageView) this.findViewById(R.id.choose);
        choose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onChoosePicListener != null) {
                    onChoosePicListener.onChoose();
                }
            }
        });
        ViewTreeObserver vto = et.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                width = et.getWidth();
                getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }

    /**
     * 设置图片宽度，默认为控件的宽度
     *
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    public EditText getEditText() {
        return et;
    }

    /**
     * 设置图片宽度，默认为控件的宽度
     *
     * @param htmlFile 上传图片的本地文件和服务器图片路径
     */
    public void setUploadPath(HtmlFile htmlFile) {
        insertImageSpan(htmlFile);
    }

    /**
     * 设置图片宽度，默认为控件的宽度
     *
     * @param fileList 上传图片的本地文件和服务器图片的集合
     */
    public void setUploadPaths(List<HtmlFile> fileList) {
        for (int i = 0; i < fileList.size(); i++) {
            insertImageSpan(fileList.get(i));
        }
    }

    private void insertImageSpan(HtmlFile htmlFile) {
        // 根据Bitmap对象创建ImageSpan对象
        // 计算缩放比例
        Bitmap loadedImage = BitmapFactory.decodeFile(htmlFile.getLocalPath());
        float scaleWidth = ((float) width) / loadedImage.getWidth();
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleWidth);
        loadedImage = Bitmap.createBitmap(loadedImage, 0, 0, loadedImage.getWidth(), loadedImage.getHeight(), matrix,
                true);
        ImageSpan imageSpan = new ImageSpan(context, loadedImage);
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        String tempUrl = "<img src=\"" + htmlFile.getUrlPath() + "\" />";
        SpannableString spannableString = new SpannableString(tempUrl);
        // 用ImageSpan对象替换你指定的字符串
        spannableString.setSpan(imageSpan, 0, tempUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        int index = et.getSelectionStart(); // 获取光标所在位置
        Editable edit_text = et.getEditableText();
        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
        edit_text.insert(index + spannableString.length(), "\n");
        LogUtil.d("插入的图片：" + spannableString.toString());
    }

}
