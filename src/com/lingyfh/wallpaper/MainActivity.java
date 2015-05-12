package com.lingyfh.wallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String tag = MainActivity.class.getSimpleName();

	private TextView mDescTextView;
	private View mSetVertical;
	private View mSetHorizontal;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
	}

	private void initView() {
		mDescTextView = (TextView) findViewById(R.id.desc_tv);
		mSetVertical = findViewById(R.id.set_vertical);
		mSetHorizontal = findViewById(R.id.set_horizontal);

		resetDesc();

		mSetVertical.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				setVerticalWallpaper(v);
			}
		});

		mSetHorizontal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				setHorizontalWallpaper(v);
			}
		});
	}

	/**
	 * 刷新描述信息
	 */
	private void resetDesc() {
		WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
		String deviceInfo = "设备信息:\n" + "W:" + DeviceUtil.getDisplayW(this)
				+ " H:" + DeviceUtil.getDisplayH(this) + "\n";
		String wallpaper = "壁纸信息:\n" + "W:"
				+ wallpaperManager.getDesiredMinimumWidth() + " H:"
				+ wallpaperManager.getDesiredMinimumHeight() + "\n";
		String currentWp = "当前壁纸模式:\n";
		if (wallpaperManager.getDesiredMinimumWidth() > wallpaperManager
				.getDesiredMinimumHeight()) {
			currentWp = currentWp + "滚屏";
		} else {
			currentWp = currentWp + "竖屏";
		}
		currentWp += "\n";
		mDescTextView.setText(deviceInfo + wallpaper + currentWp);
	}

	/**
	 * 设置滚屏壁纸
	 * 
	 * @param v
	 */
	private void setHorizontalWallpaper(final View v) {
		WpSetUtil.setWallpaper(v.getContext(),
				DeviceUtil.getDisplayW(v.getContext()) * 2,
				DeviceUtil.getDisplayH(v.getContext()), R.drawable.horizontal,
				new WpSetUtil.OnSetListener() {
					@Override
					public void onStart() {
						Toast.makeText(v.getContext(), "start setting",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onFinish(boolean result) {
						resetDesc();
						if (result) {
							Toast.makeText(v.getContext(), "success",
									Toast.LENGTH_SHORT).show();
							return;
						}

						Toast.makeText(v.getContext(), "failed",
								Toast.LENGTH_SHORT).show();

					}
				});
	}

	/**
	 * 设置竖屏壁纸
	 * 
	 * @param v
	 */
	private void setVerticalWallpaper(final View v) {
		WpSetUtil.setWallpaper(v.getContext(),
				DeviceUtil.getDisplayW(v.getContext()),
				DeviceUtil.getDisplayH(v.getContext()), R.drawable.vertical,
				new WpSetUtil.OnSetListener() {
					@Override
					public void onStart() {
						Toast.makeText(v.getContext(), "start setting",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onFinish(boolean result) {
						resetDesc();
						if (result) {
							Toast.makeText(v.getContext(), "success",
									Toast.LENGTH_SHORT).show();
							return;
						}

						Toast.makeText(v.getContext(), "failed",
								Toast.LENGTH_SHORT).show();

					}
				});
	}
}
