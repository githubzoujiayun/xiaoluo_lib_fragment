package com.xiaoluo.fragment;

import com.xiaoluo.home.HomeActivity;
import com.xiaoluo.home.R;
import com.xiaoluo.utilities.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Copyright 2014 Xiaoluo's Studio
 * 
 * @author xiaoluo 
 * @version create time: 2014年8月6日 - 上午10:22:42
 */
public abstract class BaseActivity extends Activity {
	private boolean isStartActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(MyApplication.mAppState == -1) {
			// 应用没有完全初始化， 需要重启应用
			restartApplication();
		} else {
			setContentView();
			initializeData();
			initializeViews();
		}
	}
	
	protected void setContentView(int layoutResID, boolean isShowTitle) {
		setContentView(layoutResID);
	}
	
	protected abstract void setContentView();
	protected abstract void initializeData();
	protected abstract void initializeViews();
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	protected void restartApplication() {
		finish();
		Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(Constants.RESTART_APP, true);
		startActivity(intent);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		isStartActivity = true;
		overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode,
			Bundle options) {
		super.startActivityForResult(intent, requestCode, options);
		isStartActivity = true;
		overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
	}

	@Override
	public void finish() {
		super.finish();
		if(!isStartActivity) {
			overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
		}
	}
}
