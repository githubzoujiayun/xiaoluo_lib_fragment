package com.xiaoluo.fragment;

import android.app.Application;
import android.content.Context;

/**
 * Copyright 2014 Xiaoluo's Studio
 * 
 * @author xiaoluo 
 * @version create time: 2014年8月6日 - 上午10:54:00
 */
public class MyApplication extends Application {
	public static Context mContext;
	public static int mAppState;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mAppState = -1;
		mContext = this.getApplicationContext();
	}
	
	public synchronized static void setAppState(int state) {
		mAppState = state;
	}
	
	public static Object getCurrentUsers() {
		return null;
	}
}
