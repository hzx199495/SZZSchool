package com.shizhanzhe.szzschool.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

/**
 * Created by hasee on 2016/11/10.
 */
public class MyDialog extends Dialog {
    public MyDialog(Context context) {
        super(context, android.R.style.Theme);
        setOwnerActivity((Activity)context);
    }
}
