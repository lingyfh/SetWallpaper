package com.lingyfh.wallpaper;

import android.content.Context;
import android.util.DisplayMetrics;

public class DeviceUtil {

	/**
	 * 获取DisplayMetrics
	 * 
	 * @param context
	 * @return DisplayMetrics
	 */
	public static DisplayMetrics getDisplayMetrics(Context context) {
		if (context == null) {
			return null;
		}
		return context.getResources().getDisplayMetrics();
	}

	/**
	 * 获取显示器 宽度
	 * 
	 * @param context
	 * @return int
	 */
	public static int getDisplayW(Context context) {
		return getDisplayMetrics(context).widthPixels;
	}

	/**
	 * 获取显示器 高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getDisplayH(Context context) {
		return getDisplayMetrics(context).heightPixels;
	}
}
