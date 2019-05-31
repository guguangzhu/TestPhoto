package com.photo.testphoto.utils.cache;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareData {

	private static final String SP_NAME = "jiandan";
	public static final String FIRST_OPEN_CODE ="FIRST_OPEN_CODE";   //是否显示引导页
	public static final String DOWNLOAD_CACHE_ID = "download_cache_id";//下载中断时缓存到本地的ID；

	private static SharedPreferences sp;

	public static void init(Context context){
		if (sp == null){
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
	}

	public static int getShareIntData(String key) {
		return sp.getInt(key, 0);
	}

	public static void setShareIntData(String key, int value) {
		sp.edit().putInt(key, value).commit();
	}

	public static String getShareStringData(String key) {
		return sp.getString(key, "");
	}

	public static void setShareStringData(String key, String value) {
		sp.edit().putString(key, value).commit();
	}

	public static boolean getShareBooleanData(String key){
		return sp.getBoolean(key, false);
	}

	public static void setShareBooleanData(String key, boolean value){
		sp.edit().putBoolean(key, value).commit();
	}

	public static float getShareFloatData(String key) {
		return sp.getFloat(key, 0f);
	}

	public static void setShareFloatData(String key, float value) {
		sp.edit().putFloat(key, value).commit();
	}

	public static long getShareLongData(String key) {
		return sp.getLong(key, 0l);
	}

	public static void setShareLongData(String key, long value) {
		sp.edit().putLong(key, value).commit();
	}

	public static void deleShareData(String key) {
		sp.edit().remove(key).commit();
	}

	public static void clearShareData() {
		sp.edit().clear().commit();
	}
}
