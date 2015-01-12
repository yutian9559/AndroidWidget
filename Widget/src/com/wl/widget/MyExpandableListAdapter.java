package com.wl.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

/**
 * 
 * @author 王雷(johnlwang) 
 * 对带二级菜单的列表进行了封装，原因与MyAdapter类似
 */
public abstract class MyExpandableListAdapter extends BaseExpandableListAdapter {
	protected abstract void bindChildView(int groupPosition, int childPosition,
			boolean isLastChild, View v, ViewGroup parent);

	protected abstract void bindGroupView(int groupPosition,
			boolean isExpanded, View v, ViewGroup parent);

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View v = null;

		if (isNewChildView(groupPosition, childPosition, isLastChild,
				convertView, parent)) {
			v = newChildView(groupPosition, childPosition, isLastChild, parent);
		} else {
			v = convertView;
		}

		bindChildView(groupPosition, childPosition, isLastChild, v, parent);
		return v;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View v = null;

		if (isNewGroupView(groupPosition, isExpanded, convertView, parent)) {
			v = newGroupView(groupPosition, isExpanded, parent);
		} else {
			v = convertView;
		}

		bindGroupView(groupPosition, isExpanded, v, parent);
		return v;
	}

	protected boolean isNewChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		return convertView == null;
	}

	protected boolean isNewGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		return convertView == null;
	}

	protected abstract View newChildView(int groupPosition, int childPosition,
			boolean isLastChild, ViewGroup parent);

	protected abstract View newGroupView(int groupPosition, boolean isExpanded,
			ViewGroup parent);
}