package com.shizhanzhe.szzschool.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;


public class SharedUtils {
	private static final String FILE_NAME="WelcomeFile";
	private static final String MODE_NAME="welcome";

	public static boolean isFirstStart(Context context)
	{


		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(MODE_NAME, true);

	}

	public static void putIsFirstStart(Context context,boolean isFirst){
		Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
		editor.putBoolean(MODE_NAME, isFirst);
		editor.commit();
	}


}
