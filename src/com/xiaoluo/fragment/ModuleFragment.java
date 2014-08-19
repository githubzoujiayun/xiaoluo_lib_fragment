package com.xiaoluo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Copyright 2014 Xiaoluo's Studio
 * 
 * @author xiaoluo
 * @version create time: 2014年8月19日 - 下午2:34:16
 */
public class ModuleFragment extends BaseFragment {

	private String content;

	public static ModuleFragment newInstance(String tabName) {
		ModuleFragment fragment = new ModuleFragment();
		Bundle args = new Bundle();
		args.putString("fragment_key", tabName);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		content = getArguments().getString("fragment_key");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		TextView tv = new TextView(getActivity());
		tv.setText(content);

		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
