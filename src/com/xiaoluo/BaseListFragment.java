package com.xiaoluo;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xiaoluo.BaseFragment;
import com.xiaoluo.MyApplication;
import com.xiaoluo.entities.CategoryEntity;
import com.xiaoluo.entities.ModuleEntity;
import com.xiaoluo.home.R;
import com.xiaoluo.net.Request;
import com.xiaoluo.net.Request.RequestMethod;
import com.xiaoluo.net.callback.JsonCallback;
import com.xiaoluo.pulltorefresh.PullToRefreshListView;
import com.xiaoluo.pulltorefresh.PullToRefreshListView.OnRefreshListener;
import com.xiaoluo.utilities.Trace;
import com.xiaoluo.utilities.UrlHelper;

/**
 * Copyright 2014 Xiaoluo's Studio
 * 
 * @author xiaoluo
 * @version create time: 2014年8月20日 - 上午1:16:28
 */
public abstract class BaseListFragment extends BaseFragment implements OnRefreshListener, OnItemClickListener {
	protected PullToRefreshListView mFragmentLv;
	protected BaseListAdapter mBaseListAdapter;
	protected ArrayList<Object> mEntities = new ArrayList<Object>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_module, null);
		mFragmentLv = (PullToRefreshListView) view
				.findViewById(R.id.mFragmentModuleListLsv);
		mBaseListAdapter = new BaseListAdapter();
		mFragmentLv.setAdapter(mBaseListAdapter);
		mFragmentLv.setOnRefreshListener(this);
		mFragmentLv.setOnItemClickListener(this);
		return view;
	}

	public class BaseListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mEntities != null ? mEntities.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return mEntities.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getAdapterViewAtPosition(position, convertView, parent);
		}
	}

	@Override
	public void fetchObjectData() {
		onRefresh();
	}

	public abstract View getAdapterViewAtPosition(int position, View convertView,
			ViewGroup parent);

	public abstract void onItemClick(AdapterView<?> parent, View view, int position,
			long id);
}
