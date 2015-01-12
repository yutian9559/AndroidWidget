package com.wl.widget;

import android.os.Handler;
import android.os.Message;

/**
 * 
 * @author 王雷(johnlwang) 
 * 定时器
 */
public abstract class Timer extends Handler {
	private long mTimer = 30;
	private long mLastTimer = 0;
	private boolean is_running = false;

	public Timer() {
		super();
	}

	@Override
	public void handleMessage(Message msg) {
		update();
	}

	private void sleep(long delayMillis) {
		removeMessages(0);
		sendMessageDelayed(obtainMessage(0), delayMillis);
	}

	private void update() {
		long now = System.currentTimeMillis();
		long realTimer = now - mLastTimer;

		if (realTimer > mTimer) {
			mLastTimer = now;
			OnTimer();
		}

		if (is_running) {
			sleep(mTimer);
		} else {
			OnTimerStop();
		}
	}

	/**
	 * 启动定时器
	 */
	public void startTimer() {
		is_running = true;
		OnTimerStart();
		update();
	}

	/**
	 * 关闭定时器
	 */
	public void stopTimer() {
		is_running = false;
	}

	/**
	 * 判断定时器是否在运行
	 * @return 是否在运行，true：在运行；false：不在运行
	 */
	public boolean getIsRunning() {
		return is_running;
	}

	/**
	 * 设置定时器的周期
	 * @param timer 周期，单位毫秒
	 */
	public void setTimer(long timer) {
		mTimer = timer;
	}

	/**
	 * 定时器启动的监听接口
	 */
	protected abstract void OnTimerStart();

	/**
	 * 定时器运行时的监听接口
	 */
	protected abstract void OnTimer();

	/**
	 * 定时器关闭时的监听接口
	 */
	protected abstract void OnTimerStop();
}