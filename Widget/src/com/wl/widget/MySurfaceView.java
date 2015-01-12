package com.wl.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * 
 * @author 王雷(johnlwang) 
 * 对SurfaceView进行封装
 * 创建原因：
 * 1 SurfaceView比普通的View快；
 * 2 比GLSurfaceView慢，不过容易掌握；
 * 3 使用起来有太多繁杂且重复的步骤；
 * 4 让程序员可以专心于绘制和逻辑这种正经事上。
 */
public abstract class MySurfaceView extends SurfaceView implements Callback {
	private MySurfaceViewThread mThread;
	private long mCurTime = 0;

	public MySurfaceView(Context context) {
		super(context);
		init(context);
	}

	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mCurTime = SystemClock.elapsedRealtime();
		mThread = new MySurfaceViewThread(holder);
		mThread.setRunning(true);
		mThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		mThread.setRunning(false);
		while (retry) {
			try {
				mThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	class MySurfaceViewThread extends Thread {
		private boolean mRunning = false;
		private boolean mPlaying = true;
		private SurfaceHolder mSurfaceHolder;

		public MySurfaceViewThread(SurfaceHolder surfaceHolder) {
			mSurfaceHolder = surfaceHolder;
		}

		public void setRunning(boolean running) {
			mRunning = running;
		}

		public void setPlaying(boolean playing) {
			mPlaying = playing;
		}

		@Override
		public void run() {
			while (mRunning) {
				Canvas c = null;
				try {
					c = mSurfaceHolder.lockCanvas(null);
					synchronized (mSurfaceHolder) {
						if (mPlaying) {
							long curTime = SystemClock.elapsedRealtime();
							long delta = curTime - mCurTime;
							mCurTime = curTime;
							onUpdate(delta);
						}

						if (c != null) {
							doDraw(c);
						}
					}
				} finally {
					// do this in a finally so that if an exception is thrown
					// during the above, we don't leave the Surface in an
					// inconsistent state
					if (c != null) {
						mSurfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		mThread.setPlaying(hasWindowFocus);
	}

	/**
	 * 在画布上绘图
	 * @param canvas 画布
	 */
	abstract protected void doDraw(Canvas canvas);

	/**
	 * 更新逻辑
	 * @param delta 更新的时间段，单位毫秒
	 */
	abstract protected void onUpdate(long delta);
}