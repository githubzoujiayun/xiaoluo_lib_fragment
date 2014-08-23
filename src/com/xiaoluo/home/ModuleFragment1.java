package com.xiaoluo.home;

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
import com.xiaoluo.BaseListFragment;
import com.xiaoluo.MyApplication;
import com.xiaoluo.entities.CategoryEntity;
import com.xiaoluo.entities.ModuleEntity;
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
public class ModuleFragment1 extends BaseListFragment {
	private ModuleEntity mModuleEntity;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ViewHolder mViewHolder;

	public static BaseFragment newInstance(ModuleEntity moduleEntity) {
		BaseFragment fragment = new ModuleFragment1();
		Bundle args = new Bundle();
		args.putSerializable("fragment_key", moduleEntity);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mModuleEntity = (ModuleEntity) getArguments().getSerializable(
				"fragment_key");
		// onRefresh();
		setArrayListToCategoryEntity();
	}

	private void setArrayListToCategoryEntity() {
	}

	private final class ViewHolder {
		public ImageView mCategoryIcon;
		public TextView mCategoryLabel;
	}

	@Override
	public void onRefresh() {
		// Trace.d("ModuleFragment.java:onRefresh():" +
		// mModuleEntity.getGroupId());
		int mode = 0;
		final Request request = new Request(UrlHelper.getCategoryUrl(
				mModuleEntity.getGroupId(), mode), RequestMethod.GET);

		request.setCallback(new JsonCallback<ArrayList<CategoryEntity>>() {

			@Override
			public void onFailure(Exception result) {
				Trace.d("ModuleFragment.java:onFailure():" + result);
				mFragmentLv.onRefreshComplete();
			}

			@Override
			public void onSuccess(ArrayList<CategoryEntity> result) {
				Trace.d("ModuleFragment.java:onSuccess():" + result.size());
				mFragmentLv.onRefreshComplete();
				// TODO:处理刷新之后,之前的数据是否清理,在此处添加判断
				// mEntities.clear();

				mEntities.addAll(result);
				// for(CategoryEntity entity : result) {
				// Trace.d("ModuleFragment.java:onSuccess():Foreach:"+
				// entity.getIconUrl());
				// }
				mFragmentLv.onRefreshComplete();
				mBaseListAdapter.notifyDataSetChanged();
			}

			@Override
			public ArrayList<CategoryEntity> onPreHandler(
					ArrayList<CategoryEntity> t) {
				// Trace.d("ModuleFragment.java:onPreHandler():" +
				// t.toString());
				return t;
			}
		}.setReturnType(new TypeToken<ArrayList<CategoryEntity>>() {
		}.getType()));
		request.execute();
	}

	@Override
	public View getAdapterViewAtPosition(int position, View convertView,
			ViewGroup parent) {
		if (convertView == null || convertView.getTag() == null) {
			mViewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getActivity()).inflate(
					R.layout.fragment_list_module_item1, null);

			mViewHolder.mCategoryIcon = (ImageView) convertView
					.findViewById(R.id.mCategoryIcon);
			mViewHolder.mCategoryLabel = (TextView) convertView
					.findViewById(R.id.mCategoryLabel);

			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		CategoryEntity category = (CategoryEntity) mEntities.get(position);
		// Trace.d("CategoryAdapter:getView().Url:" + category.getIconUrl());
		// Trace.d("CategoryAdapter:getView().Description:"
		// + category.getDescription());
		// =========================================================
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		imageLoader.displayImage(category.getIconUrl(),
				mViewHolder.mCategoryIcon, MyApplication.getmImageOptions());
		// mViewHolder.mCategoryIcon.setBackgroundResource(R.drawable.ic_launcher);
		mViewHolder.mCategoryLabel.setText(category.getDescription());

		return convertView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		position = position - 1;
		Toast.makeText(
				getActivity(),
				(String) ((CategoryEntity) mBaseListAdapter.getItem(position))
						.getDescription() + ":" + position, Toast.LENGTH_SHORT)
				.show();
	}
}
