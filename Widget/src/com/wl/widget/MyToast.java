package com.wl.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * 
 * @author 王雷
 * 浮空小部件，原理和Toast类似，所以取名MyToast，可用于桌面装饰之类的
 */
public class MyToast {
	private static final String TAG = "MyToast";
	private WindowManager mWindowManager;
	private Context mContext;
	private LayoutParams mLayoutParams = new LayoutParams();
	private View mView = null;
	private boolean mIsVisibe = false;

	/**
	 * 初始化函数
	 * @param context 上下文
	 * @param view 小部件对应的布局
	 */
	public MyToast(Context context, View view) {
		mContext = context;
		mWindowManager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		mLayoutParams.height = LayoutParams.WRAP_CONTENT;
		mLayoutParams.width = LayoutParams.WRAP_CONTENT;
		mLayoutParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE
				| LayoutParams.FLAG_LAYOUT_NO_LIMITS
				| LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		// mLayoutParams.flags |= LayoutParams.FLAG_NOT_TOUCHABLE;
		mLayoutParams.format = PixelFormat.TRANSLUCENT;
		mLayoutParams.windowAnimations = 0;// android.R.style.Animation_Toast;
		mLayoutParams.type = LayoutParams.TYPE_SYSTEM_ERROR;
		mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
		mLayoutParams.setTitle("Toast");
		mView = view;
	}

	/**
	 * 显示该浮空小部件
	 */
	public void startHandler() {
		if (!mIsVisibe) {
			mIsVisibe = true;
			try {
				mWindowManager.addView(mView, mLayoutParams);
			} catch (IllegalStateException e) {
				mWindowManager.updateViewLayout(mView, mLayoutParams);
				Log.e(TAG, "WL_DEBUG startHandler error : " + e);
			}
		}
	}

	/**
	 * 隐藏该浮空小部件
	 */
	public void stopHandler() {
		if (mIsVisibe) {
			mIsVisibe = false;
			try {
				mWindowManager.removeView(mView);
			} catch (IllegalArgumentException e) {
				Log.e(TAG, "WL_DEBUG stopHandler error : " + e);
			}
		}
	}

	/**
	 * 设置左上角坐标
	 * @param left 左x坐标
	 * @param top 上y坐标
	 */
	public void setLayout(int left, int top) {
		mLayoutParams.x = left;
		mLayoutParams.y = top;
	}

	/**
	 * 设置宽高
	 * @param width 宽
	 * @param height 高
	 */
	public void setSize(int width, int height) {
		mLayoutParams.width = width;
		mLayoutParams.height = height;
	}

	/**
	 * 设置左上角坐标并更新界面
	 * @param left 左x坐标
	 * @param top 上y坐标
	 */
	public void layout(int left, int top) {
		setLayout(left, top);
		update();
	}

	/**
	 * 获取左x坐标
	 * @return 左x坐标
	 */
	public int getLeft() {
		return mLayoutParams.x;
	}

	/**
	 * 获取上y坐标
	 * @return 上y坐标
	 */
	public int getTop() {
		return mLayoutParams.y;
	}

	public void setGravity(int gravity) {
		mLayoutParams.gravity = gravity;
	}

	public void setFlag(int flags) {
		mLayoutParams.flags = flags;
	}

	public void addFlag(int flags) {
		mLayoutParams.flags |= flags;
	}

	/**
	 * 更新界面
	 */
	public void update() {
		if (mIsVisibe) {
			try {
				mWindowManager.updateViewLayout(mView, mLayoutParams);
			} catch (IllegalArgumentException e) {
				Log.e(TAG, "WL_DEBUG layout error : " + e);
			}
		}
	}

	/**
	 * 判断该小部件是否可见
	 * @return true：可见；false：不可见。
	 */
	public boolean getIsVisible() {
		return mIsVisibe;
	}

	public void setWindowAnimations(int windowAnimations) {
		mLayoutParams.windowAnimations = windowAnimations;
	}

	public void setType(int type) {
		mLayoutParams.type = type;
	}
}