package com.lingyfh.wallpaper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;

/**
 * Created by lingyfh on 15/5/12.
 */
public class WpSetUtil {

	private static final String tag = WpSetUtil.class.getSimpleName();

	public interface OnSetListener {
		public void onStart();
		public void onFinish(boolean result);
	}

	/**
	 * 设置壁纸
	 * 
	 * @param context
	 * @param wpWidth
	 *            壁纸宽度
	 * @param wpHight
	 *            壁纸高度
	 * @param resID
	 *            壁纸资源ID
	 * @param listener
	 */
	public static void setWallpaper(final Context context, final int wpWidth,
			final int wpHight, final int resID, final OnSetListener listener) {
		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				if (listener != null) {
					listener.onStart();
				}
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				Bitmap bitmap = BitmapFactory.decodeResource(
						context.getResources(), resID);
				bitmap = Bitmap.createScaledBitmap(bitmap, wpWidth, wpHight,
						false);
				return setWallpaper(context, wpWidth, wpHight, bitmap);
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				if (listener != null) {
					listener.onFinish(result);
				}
			}
		}.execute();
	}

	/**
	 * 设置壁纸
	 * 
	 * @param context
	 * @param wpWidth
	 * @param wpHight
	 * @param bitmap
	 * @return
	 */
	private static boolean setWallpaper(final Context context,
			final int wpWidth, final int wpHight, Bitmap bitmap) {
		InputStream inputStream = null;
		try {
			inputStream = bitmap2InputStream(context, bitmap);
			setWallpaper(context, wpWidth, wpHight, inputStream);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * bitmap 转换成inputstream
	 * 
	 * @param context
	 * @param bitmap
	 * @return
	 * @throws IOException
	 */
	private static InputStream bitmap2InputStream(Context context, Bitmap bitmap)
			throws IOException {
		File file = new File(context.getFilesDir(), "wallpaper.jpg");
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
		fileOutputStream.close();
		return filePath2InputStream(file.getAbsolutePath());
	}

	/**
	 * filePath 转换成inputstream
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private static InputStream filePath2InputStream(String filePath)
			throws IOException {
		if (TextUtils.isEmpty(filePath)) {
			return null;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		return new FileInputStream(file);
	}

	/**
	 * res转换成inputstream
	 * 
	 * @param context
	 * @param resID
	 * @return
	 */
	private static InputStream res2InputStream(Context context, int resID) {
		return context.getResources().openRawResource(resID);
	}

	/**
	 * 将inputstream设置为壁纸
	 * 
	 * @param context
	 * @param wpWidth
	 * @param wpHight
	 * @param is
	 */
	private static void setWallpaper(final Context context, final int wpWidth,
			final int wpHight, final InputStream is) {
		WallpaperManager wallpaperManager = WallpaperManager
				.getInstance(context);
		try {
			wallpaperManager.suggestDesiredDimensions(wpWidth, wpHight);
			// 使用setBitmap方法图片有一定几率拉伸
			// wallpaperManager.setBitmap(bitmap);
			wallpaperManager.setStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
