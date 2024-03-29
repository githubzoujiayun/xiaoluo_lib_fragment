package com.xiaoluo;

import com.xiaoluo.home.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Copyright 2014 Xiaoluo's Studio
 * 
 * @author xiaoluo 
 * @version create time: 2014年8月6日 - 上午10:48:42
 */
public abstract class BaseFragment extends Fragment {
	private boolean isReadyToFetchObjectData;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		isReadyToFetchObjectData = true;
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
	}

	public void startActivityForResult(Intent intent, int requestCode,
			Bundle options) {
		super.startActivityForResult(intent, requestCode);
		getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
	}

	public abstract void fetchObjectData();
	
	public boolean isReadyToFetchObjectData() {
		return isReadyToFetchObjectData;
	}
}
