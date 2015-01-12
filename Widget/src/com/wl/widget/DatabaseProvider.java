package com.wl.widget;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * 
 * @author 王雷(johnlwang) 
 * 创建原因：Android中的ContentProvider是为了封装外部存储，大部分情况下都是数据库在用，方便程序中的Cursor和CursorAdapter使用，
 * 不过Android居然不专门写个数据库的ContentProvider，因此我就简单实现了一下，适用于简单的数据库操作，我的日志http://user.qzone.qq.com/441892194/blog/1313494620便是该类的前身
 */
public abstract class DatabaseProvider extends ContentProvider
{
	private static final String TAG = "DatabaseContentProvider";
	private SQLiteOpenHelper mOpenHelper = null;

	/**
	 * 根据URI获取表名
	 * @param uri 格式："content://" + 包名 + "/" + 表名
	 * @return 表名
	 */
	private String getTable(Uri uri) {
		return uri.getPathSegments().get(0);
	}

	/**
	 * 创建数据库管理类
	 * @return 具体的数据库管理类
	 */
	protected abstract SQLiteOpenHelper CreateOpenHelper();

	@Override
	public String getType(Uri uri) {
		return ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + getTable(uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String table = getTable(uri);
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count = db.delete(table, selection, selectionArgs);

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		String table = getTable(uri);
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		long rowId = db.insert(table, null, values);
		Uri newUri = ContentUris.withAppendedId(uri, rowId);

        getContext().getContentResolver().notifyChange(newUri, null);
		return newUri;
	}

	@Override
	public boolean onCreate() {
		mOpenHelper = CreateOpenHelper();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String table = getTable(uri);
		qb.setTables(table);
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		Cursor ret = qb.query(db, projection, selection, selectionArgs,
				null, null, sortOrder);

		if (ret == null) {
			Log.e(TAG, "WL_DEBUG query: failed");
		} else {
			ret.setNotificationUri(getContext().getContentResolver(), uri);
		}

		return ret;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count = -1;
		String table = getTable(uri);

		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		
		try
		{
			count = db.update(table, values, selection, selectionArgs);
		}
		catch (SQLiteConstraintException e)
		{
			count = -1;
			Log.e(TAG, "WL_DEBUG update error : " + e);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}