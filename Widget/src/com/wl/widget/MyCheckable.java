package com.wl.widget;

import android.widget.Checkable;

/**
 * 
 * @author 王雷(johnlwang) 
 * 对boolean型变量进行了封装，监测变量变化
 */
public class MyCheckable implements Checkable {
	private boolean mChecked;
	private MyCheckableListener mListener;

	public MyCheckable(boolean checked, MyCheckableListener listener) {
		this(checked, false, listener);
	}

	public MyCheckable(boolean checked, boolean callListenerForFirstTime,
			MyCheckableListener listener) {
		mListener = listener;

		if (callListenerForFirstTime) {
			setValueInternal(checked);
		} else {
			mChecked = checked;
		}
	}
	
	private void setValueInternal(boolean checked) {
		mChecked = checked;
		if (mListener != null) {
			mListener.onValueChange(this, mChecked);
		}
	}

	public interface MyCheckableListener {
		/**
		 * 监测变量变化接口
		 * @param myCheckable 当前对象
		 * @param value 当前对象的值
		 */
		void onValueChange(MyCheckable myCheckable, boolean value);
	}

	@Override
	public boolean isChecked() {
		return mChecked;
	}

	@Override
	public void setChecked(boolean checked) {
		if (mChecked != checked) {
			setValueInternal(checked);
		}
	}

	@Override
	public void toggle() {
		setChecked(!mChecked);
	}
}