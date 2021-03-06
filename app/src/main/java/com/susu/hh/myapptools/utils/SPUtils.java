package com.susu.hh.myapptools.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPUtils {

	public static String getString(Context ctx, String key){
		SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getString(key, "");
	}
	
	public static int getInt(Context ctx, String key){
		SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getInt(key, 0);
	}
	
	public static boolean getBoolean(Context ctx, String key){
		SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getBoolean(key, false);
	}
	
	public static void put(Context ctx, String key , Object value){
		SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		if(value instanceof String){
			edit.putString(key, (String) value);
		}else if( value instanceof Boolean){
			edit.putBoolean(key, (Boolean) value);
		}else if( value instanceof Integer){
			edit.putInt(key, (Integer) value);
		}
		edit.commit();
	}
	
}
