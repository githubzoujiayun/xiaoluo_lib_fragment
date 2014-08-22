package com.xiaoluo.pulltorefresh;

import com.xiaoluo.home.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Copyright 2014 Xiaoluo's Studio
 * 
 * @author xiaoluo 
 * @version create time: 2014年8月21日 - 下午4:32:08
 */
public class LoadMoreListView extends ListView {
	
	protected static final String TAG = "LoadMoreListView";
	private View mFooterView;
	private OnScrollListener mOnScrollListener;
	private OnLoadMoreListener mOnLoadMoreListener;
	
	/**
	 * 现在是否导入
	 */
	private boolean mIsLoading;
	
	private int mCurrentScrollState;
	
	public LoadMoreListView(Context context) {
		super(context);
		init(context);
	}

	public LoadMoreListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	/**
	 * 初始化
	 * @param context
	 */
	private void init(Context context) {
		mFooterView = View.inflate(context, R.layout.load_more_footer, null);
		addFooterView(mFooterView);
		hideFooterView();
		
		/**
		 * 当回调这个视图的setOnScrollListener方法时,在这里必须使用super.setOnScrollListener()来避免重写
		 */
		super.setOnScrollListener(superOnScrollListener);
	}
	
	/**
	 * 显示导入更多视图
	 */
	private void showFooterView() {
		mFooterView.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 隐藏导入更多视图
	 */
	private void hideFooterView() {
		mFooterView.setVisibility(View.GONE);
	}
	
	@Override
	public void setOnScrollListener(OnScrollListener l) {
		this.mOnScrollListener = l;
	}
	
	/**
	 * 设置导入更多监听事件, 通常情况, 你应该在这里导入更多的数据处理
	 * @param listener
	 */
	public void setOnLoadMoreListener(OnLoadMoreListener listener) {
		this.mOnLoadMoreListener = listener;
	}
	
	/**
	 * 当导入更多的数据完成后,你必须使用这个方法来隐藏"导入更多"的视图, 要不然这个视图会时时刻刻显示在窗口上
	 */
	public void onLoadMoreComplete() {
		mIsLoading = false;
		hideFooterView();
	}

	private OnScrollListener superOnScrollListener = new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			mCurrentScrollState = scrollState;
			// 使用setOnScrollListener时避免重写
			if(mOnScrollListener != null) {
				mOnScrollListener.onScrollStateChanged(view, scrollState);
			}
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if(mOnScrollListener != null) {
				mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
			
			if(visibleItemCount == totalItemCount) {
				// 如果所有的Item不能充满整个视图的话,我们应该使"导入更多"的视图隐藏起来
				hideFooterView();
			} else if(!mIsLoading 
					&& (firstVisibleItem + visibleItemCount) >= totalItemCount
					&& mCurrentScrollState != SCROLL_STATE_IDLE) {
				showFooterView();
				mIsLoading = true;
				
				if(mOnLoadMoreListener != null) {
					mOnLoadMoreListener.onLoadMore();
				}
			}
		}
	};
	
	/**
	 * 导入更多监听事件的接口
	 */
	public interface OnLoadMoreListener {
		/**
		 * 导入更多数据处理
		 */
		void onLoadMore();
	}
}
