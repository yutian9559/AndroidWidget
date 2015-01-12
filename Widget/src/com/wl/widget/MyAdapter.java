package com.wl.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 
 * @author 王雷(johnlwang) 
 * 创建原因：工作中的自定义Adapter在实现getView接口时总要在判断convertView ==
 *         null时新建一个view出来，这种重复劳动多了以后总是让人感觉烦燥，
 *         因此我仿造了CursorAdapter的形式将getView分解成bindView和newView两个方法
 */
public abstract class MyAdapter extends BaseAdapter {
	/**
	 * 绑定View
	 */
	protected abstract void bindView(int position, View v, ViewGroup parent);

	/**
	 * 新建View
	 */
	protected abstract View newView(int position, ViewGroup parent);

	/**
	 * 判断是否需要创建View
	 * 1 简单的列表成员使用的都是同一套layout，所以一般情况下只要判断convertView == null即可，此时不用重写该函数；
	 * 2 在列表成员使用不同套layout时，需要根据不同情况选择不同的layout时需要重写该函数
	 * @return 是否需要运行newView，true：需要；false：不需要
	 */
	protected boolean isNewView(int position, View convertView, ViewGroup parent) {
		return convertView == null;
	}

	/**
	 * 获取View，继承于系统接口，任何情况下都不要重写
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;

		if (isNewView(position, convertView, parent)) {
			v = newView(position, parent);
		} else {
			v = convertView;
		}

		bindView(position, v, parent);
		return v;
	}
}