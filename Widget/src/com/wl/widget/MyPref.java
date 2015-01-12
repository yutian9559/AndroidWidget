package com.wl.widget;

import android.content.Context;
import android.preference.PreferenceManager;
import android.provider.Settings;

/**
 * 
 * @author 王雷(johnlwang) 
 * 创建原因：个人感觉Android设置存储简单变量的函数还是不够简洁，因此自己又封装了一层。
 * 必要说明：
 *         1 之所以把所有函数都设置成protected是为了让get前缀的函数不被滥用，进而造成代码中出现多处需要设置初始值的情况。
 *         2 带Public后缀的函数是为了在不同apk中共享数据，一般用于Library属性的工程，在非Library属性的工程下定义毫无意义。
 * 使用方法：直接继承该类，并用static final String定义具体的key，之后根据具体情况选用相应的get和set方法。
 *         3 get方法中的key是关键字，defValue是对应的初始值，set方法中的key是关键字，value是新值。
 * 个人建议：
 *         1 定义key时如果在当前类以外的地方没有引用到请务必使用private。
 *         2 key的命名只使用大写字母和下划线，对应的具体字符串使用相应的小写字母和下划线。
 *         
 */
public class MyPref {
	protected static boolean getBoolean(Context context, String key,
			boolean defValue) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(key, defValue);
	}

	protected static void setBoolean(Context context, String key, boolean value) {
		PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putBoolean(key, value).commit();
	}

	protected static boolean getBooleanPublic(Context context, String key,
			boolean defValue) {
		return Settings.System.getInt(context.getContentResolver(), key,
				defValue ? 1 : 0) != 0;
	}

	protected static void setBooleanPublic(Context context, String key,
			boolean value) {
		Settings.System
				.putInt(context.getContentResolver(), key, value ? 1 : 0);
	}

	protected static int getInt(Context context, String key, int defValue) {
		return PreferenceManager.getDefaultSharedPreferences(context).getInt(
				key, defValue);
	}

	protected static void setInt(Context context, String key, int value) {
		PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putInt(key, value).commit();
	}

	protected static int getIntPublic(Context context, String key, int defValue) {
		return Settings.System.getInt(context.getContentResolver(), key,
				defValue);
	}

	protected static void setIntPublic(Context context, String key, int value) {
		Settings.System.putInt(context.getContentResolver(), key, value);
	}

	protected static float getFloat(Context context, String key, float defValue) {
		return PreferenceManager.getDefaultSharedPreferences(context).getFloat(
				key, defValue);
	}

	protected static void setFloat(Context context, String key, float value) {
		PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putFloat(key, value).commit();
	}

	protected static float getFloatPublic(Context context, String key,
			float defValue) {
		return Settings.System.getFloat(context.getContentResolver(), key,
				defValue);
	}

	protected static void setFloatPublic(Context context, String key,
			float value) {
		Settings.System.putFloat(context.getContentResolver(), key, value);
	}

	protected static String getString(Context context, String key,
			String defValue) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(key, defValue);
	}

	protected static void setString(Context context, String key, String value) {
		PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putString(key, value).commit();
	}

	protected static String getStringPublic(Context context, String key,
			String defValue) {
		String result = Settings.System.getString(context.getContentResolver(),
				key);

		if (result == null) {
			result = defValue;
		}

		return result;
	}

	protected static void setStringPublic(Context context, String key,
			String value) {
		Settings.System.putString(context.getContentResolver(), key, value);
	}
	
	protected static long getLong(Context context, String key, long defValue) {
		return PreferenceManager.getDefaultSharedPreferences(context).getLong(
				key, defValue);
	}

	protected static void setLong(Context context, String key, long value) {
		PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putLong(key, value).commit();
	}

	protected static long getLongPublic(Context context, String key, long defValue) {
		return Settings.System.getLong(context.getContentResolver(), key,
				defValue);
	}

	protected static void setLongPublic(Context context, String key, long value) {
		Settings.System.putLong(context.getContentResolver(), key, value);
	}
}