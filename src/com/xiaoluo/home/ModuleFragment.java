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
public class ModuleFragment extends BaseFragment implements OnRefreshListener {
	private ModuleEntity mModuleEntity;
	private PullToRefreshListView mFragmentModuleListLsv;
	private CategoryAdapter mCategoryAdapter;
	private ArrayList<CategoryEntity> mCategoryEntities = new ArrayList<CategoryEntity>();
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	
	public static ModuleFragment newInstance(ModuleEntity moduleEntity) {
		ModuleFragment fragment = new ModuleFragment();
		Bundle args = new Bundle();
		args.putSerializable("fragment_key", moduleEntity);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mModuleEntity = (ModuleEntity) getArguments().getSerializable("fragment_key");
		onRefresh();
		setArrayListToCategoryEntity();
	}
	
	private void setArrayListToCategoryEntity() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_module, null);
		mFragmentModuleListLsv = (PullToRefreshListView) view.findViewById(R.id.mFragmentModuleListLsv);
		mCategoryAdapter = new CategoryAdapter();
		mFragmentModuleListLsv.setAdapter(mCategoryAdapter);
		
		mFragmentModuleListLsv.setOnRefreshListener(this);
		mFragmentModuleListLsv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(
						getActivity() , 
						(String) mCategoryAdapter.getItem(position), 
						Toast.LENGTH_SHORT
				).show();
			}
		});
		
		return view;
	}
	
	private class CategoryAdapter extends BaseAdapter {
		private ViewHolder mViewHolder;

		@Override
		public int getCount() {
			Trace.d("CategoryAdapter:getCount():"+ mCategoryEntities.size());
			return mCategoryEntities != null ? mCategoryEntities.size() : 0 ;
		}

		@Override
		public Object getItem(int position) {
			return mCategoryEntities.get(position).getDescription();
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null || convertView.getTag() == null) {
				mViewHolder = new ViewHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list_module_item, null);
				
				mViewHolder.mCategoryIcon = (ImageView) convertView.findViewById(R.id.mCategoryIcon);
				mViewHolder.mCategoryLabel = (TextView) convertView.findViewById(R.id.mCategoryLabel);
				
				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}
			CategoryEntity category = mCategoryEntities.get(position);
			Trace.d("CategoryAdapter:getView().Url:"+ category.getIconUrl());
			Trace.d("CategoryAdapter:getView().Description:"+ category.getDescription());
			// =========================================================
			imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
			imageLoader.displayImage(category.getIconUrl(), mViewHolder.mCategoryIcon, MyApplication.getmImageOptions());
//			mViewHolder.mCategoryIcon.setBackgroundResource(R.drawable.ic_launcher);
			mViewHolder.mCategoryLabel.setText(category.getDescription());
			
			return convertView;
		}
	}

	public final class ViewHolder {
		public ImageView mCategoryIcon;
		public TextView mCategoryLabel;
	}

	@Override
	public void onRefresh() {
		Trace.d("ModuleFragment.java:onRefresh():"+ mModuleEntity.getGroupId());
		int mode = 0;
		final Request request = new Request(
				UrlHelper.getCategoryUrl(mModuleEntity.getGroupId(), 
						mode), 
				RequestMethod.GET);
		
		request.setCallback(new JsonCallback<ArrayList<CategoryEntity>>() {

			@Override
			public void onFailure(Exception result) {
				Trace.d("ModuleFragment.java:onFailure():"+ result);
				mFragmentModuleListLsv.onRefreshComplete();
			}

			@Override
			public void onSuccess(ArrayList<CategoryEntity> result) {
				Trace.d("ModuleFragment.java:onSuccess():"+ result.size());
				mCategoryEntities = result;
//				for(CategoryEntity entity : result) {
//					Trace.d("ModuleFragment.java:onSuccess():Foreach:"+ entity.getIconUrl());
//				}
				mCategoryAdapter.notifyDataSetChanged();
				mFragmentModuleListLsv.onRefreshComplete();
			}

			@Override
			public ArrayList<CategoryEntity> onPreHandler(
					ArrayList<CategoryEntity> t) {
				Trace.d("ModuleFragment.java:onPreHandler():"+ t.toString());
				return t;
			}
		}.setReturnType(new TypeToken<ArrayList<CategoryEntity>>(){}.getType()));
		request.execute();		
	}
}
