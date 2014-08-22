package com.xiaoluo;

import com.xiaoluo.home.HomeActivity;

import android.content.Intent;
import android.os.Bundle;

/**
 * Copyright 2014 Xiaoluo's Studio
 * 
 * @author xiaoluo 
 * @version create time: 2014年8月6日 - 上午11:00:13
 */
public class SplashActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 对应用进行初始化
		MyApplication.setAppState(0);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void setContentView() {

	}

	@Override
	protected void initializeData() {

	}

	@Override
	protected void initializeViews() {
		if(getIntent() != null && getIntent().getExtras() != null) {
			Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
			intent.putExtras(getIntent().getExtras());
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
	}

}
