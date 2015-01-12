package com.wl.widget;

/**
 * 
 * @author 王雷(johnlwang) 
 * 监听int型变量变化，与MyCheckable类似
 */
public class MyInt {
	private int mValue;
	private MyIntListener mListener;

	public MyInt(int value, MyIntListener listener) {
		this(value, false, listener);
	}

	public MyInt(int value, boolean callListenerForFirstTime,
			MyIntListener listener) {
		mListener = listener;

		if (callListenerForFirstTime) {
			setValueInternal(value);
		} else {
			mValue = value;
		}
	}

	public int getValue() {
		return mValue;
	}

	public void setValue(int value) {
		if (mValue != value) {
			setValueInternal(value);
		}
	}

	private void setValueInternal(int value) {
		mValue = value;
		if (mListener != null) {
			mListener.onValueChange(this, mValue);
		}
	}

	public interface MyIntListener {
		void onValueChange(MyInt myInt, int value);
	}
}