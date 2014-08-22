package com.xiaoluo.pulltorefresh;

import com.xiaoluo.home.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 下拉刷新类 PullToRefreshListView
 * 
 * Copyright 2014 Xiaoluo's Studio
 * 
 * @author xiaoluo 
 * @version create time: 2014年8月20日 - 下午3:59:25
 */
public class PullToRefreshListView extends ListView {
	
	/**
	 * 避免把所有屏幕的高度会使顶部视图太大,所以要制定一定的比例
	 */
	private static final int RATIO = 3;
	
	private View mHeader;
	private ImageView iv_arrow;
	private ProgressBar pb_refresh;
	private TextView tv_title;
	private TextView tv_time;
	
	/**
	 * 时间格式
	 */
	private SimpleDateFormat mSimpleDateFormat;
	
	/**
	 * 顶部视图的高度
	 */
	private int mHeaderHeight;
	
	/**
	 * 当手指触摸下拉Y轴时的位置
	 */
	private int downPositionY;
	
	/**
	 * 当手指触摸移动Y轴时的位置
	 */
	private int currentPositionY;
	
	/**
	 * 下拉的距离
	 */
	private int pullDistance;
	
	/**
	 * 当前的状态
	 */
	private State mState;
	
	/**
	 * 当顺时针移动时箭头的动画效果
	 */
	private Animation animation;
	
	/**
	 * 当逆时针移动时箭头的动画效果
	 */
	private Animation reverseAnimation;
	
	/**
	 * 是否能下拉的状态
	 */
	private boolean isCanPullToRefresh;
	
	/**
	 * 如果一直下拉时没有释放,手指向上移回去,这时,我们应该把动画效果反转回去
	 */
	private boolean isBack;
	
	/**
	 * 刷新事件监听
	 */
	private OnRefreshListener mOnRefreshListener;
	
	/**
	 * 滚动事件监听
	 */
	private OnScrollListener mOnScrollListener;
	
	/**
	 * 构造函数
	 * @param context
	 */
	public PullToRefreshListView(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * 构造函数
	 * @param context
	 * @param attrs
	 */
	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * 构造函数
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public PullToRefreshListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}
	
	/**
	 * 初始化视图
	 * @param context
	 */
	private void initView(Context context) {
		// 取得context实例对应的xml里定义的view
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		mHeader = inflater.inflate(R.layout.pull_to_refresh_header, null);
		iv_arrow = (ImageView) mHeader.findViewById(R.id.iv_arrow);
		pb_refresh = (ProgressBar) mHeader.findViewById(R.id.pb_refresh);
		tv_title = (TextView) mHeader.findViewById(R.id.tv_title);
		tv_time = (TextView) mHeader.findViewById(R.id.tv_time);
		
		mSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss");
		
		// 测量视图要显示的高度
		measureHeaderView(mHeader);
		
		mHeaderHeight = mHeader.getMeasuredHeight();
		// 要使顶部视图在窗口的上方,就要使用-mHeaderHeight
		mHeader.setPadding(0, -mHeaderHeight, 0, 0);
		// 作用:使整个视图刷新,如果视图可见的话,在将来某个点onDraw(android.graphics.Canvas)被调用,这必须在UI线程中调用,从非Ui线程调用的话,得调用postInvalidate().
		mHeader.invalidate();
		addHeaderView(mHeader);
		
		mState = State.ORIGNAL;
		
		super.setOnScrollListener(new OnScrollListener() {
//			boolean isLastRow = false;
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				//正在滚动时回调，回调2-3次，手指没抛则回调2次。scrollState = 2的这次不回调    
	            //回调顺序如下    
	            //第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动    
	            //第2次：scrollState = SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）    
	            //第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动             
	            //当屏幕停止滚动时为0；当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1；  
	            //由于用户的操作，屏幕产生惯性滑动时为2  
	        
	            //当滚到最后一行且停止滚动时，执行加载    
//	            if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {    
//	                // 加载元素    
//	                // ......    
//	        
//	                isLastRow = false;    
//	            }
	            
				if(mOnScrollListener != null) {
					mOnScrollListener.onScrollStateChanged(view, scrollState);
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				//滚动时一直回调，直到停止滚动时才停止回调。单击时回调一次。    
	            //firstVisibleItem：当前能看见的第一个列表项ID（从0开始）    
	            //visibleItemCount：当前能看见的列表项个数（小半个也算）    
	            //totalItemCount：列表项共数    
	        
	            //判断是否滚到最后一行    
//	            if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
//	            	isLastRow = true;
//	            }
	            
	            if(mOnScrollListener != null) {
	            	mOnScrollListener.onScroll(view, firstVisibleItem, 
	            			visibleItemCount, totalItemCount);
	            }
	            
	            if(firstVisibleItem == 0) {
	            	isCanPullToRefresh = true;
	            } else {
	            	isCanPullToRefresh = false;
	            }
			}
		});
		
		animation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(250);
		animation.setFillAfter(true);// 动画终止时停留在最后一帧~不然会回到没有执行之前的状态
		animation.setInterpolator(new LinearInterpolator());// LinearInterpolator类表示在动画的以均匀的速率改变

		reverseAnimation = new RotateAnimation(0, -180, 
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);// 动画终止时停留在最后一帧~不然会回到没有执行之前的状态
		reverseAnimation.setInterpolator(new LinearInterpolator());// LinearInterpolator类表示在动画的以均匀的速率改变
	}
	
	/**
	 * 当显示时视图的高度将被测量
	 * @param view 将被测量的视图
	 */
	private void measureHeaderView(View view) {
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		
		if (lp == null) {
			lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		
		int childMeasureWidth = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
		int childMeasureHeight;
		if (lp.height > 0) {
			childMeasureHeight = MeasureSpec.makeMeasureSpec(lp.height, MeasureSpec.EXACTLY);
		} else {
			// 测量规范模式:父类在子类上没有施加任何限制。它可以是任何大小
			childMeasureHeight = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		view.measure(childMeasureWidth, childMeasureHeight);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int y = (int) ev.getRawY();
		int action = ev.getAction();
		
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			downPositionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			if(!isCanPullToRefresh) {
				break;
			}
			currentPositionY = y;
			pullDistance = (currentPositionY - downPositionY) / RATIO;
			
			if(mState == State.REFRESHING) {
				break;
			} else if(mState == State.ORIGNAL && pullDistance > 0) {
				mState = State.PULL_TO_REFRESH;
				changeState();
			} else if(mState == State.PULL_TO_REFRESH && pullDistance > mHeaderHeight) {
				mState = State.RELEASE_TO_REFRESH;
				changeState();
			} else if(mState == State.RELEASE_TO_REFRESH) {
				if(pullDistance < 0) {
					mState = State.ORIGNAL;
					changeState();
				} else if(pullDistance < mHeaderHeight) {
					mState = State.PULL_TO_REFRESH;
					isBack = true;
					changeState();
				}
			}
			
			if(mState != State.REFRESHING) {
				mHeader.setPadding(0, (int) (pullDistance - mHeaderHeight), 0, 0);
			}
			
			break;
		case MotionEvent.ACTION_UP:
			if(mState == State.REFRESHING) {
				break;
			} else if(mState == State.PULL_TO_REFRESH) {
				mState = State.ORIGNAL;
			} else if(mState == State.RELEASE_TO_REFRESH) {
				mState = State.REFRESHING;
			} else {
				break;
			}
			changeState();
			
			break;

		default:
			break;
		}
		
		return super.onTouchEvent(ev);
	}

	/**
	 * 当ListView处在不同状态时,改变顶部视图的状态
	 */
	private void changeState() {
		if(mState == State.ORIGNAL) {
			iv_arrow.setVisibility(View.VISIBLE);
			pb_refresh.setVisibility(View.GONE);
			tv_time.setVisibility(View.VISIBLE);
			tv_title.setVisibility(View.VISIBLE);
			iv_arrow.clearAnimation();
			
			mHeader.setPadding(0, -mHeaderHeight, 0, 0);
		} else if(mState == State.PULL_TO_REFRESH) {
			iv_arrow.setVisibility(View.VISIBLE);
			pb_refresh.setVisibility(View.GONE);
			tv_title.setVisibility(View.VISIBLE);
			tv_time.setVisibility(View.VISIBLE);
			iv_arrow.clearAnimation();
			
			tv_title.setText(getResources().getString(R.string.pull_refresh));
			if(isBack) {
				// 来自下拉刷新的刷新释放
				iv_arrow.startAnimation(animation);
				isBack = false;
			}
		} else if(mState == State.RELEASE_TO_REFRESH) {
			iv_arrow.setVisibility(View.VISIBLE);
			pb_refresh.setVisibility(View.GONE);
			tv_title.setVisibility(View.VISIBLE);
			tv_time.setVisibility(View.VISIBLE);
			iv_arrow.clearAnimation();
			
			tv_time.setText(getResources().getString(R.string.release_to_refresh));
			
			iv_arrow.startAnimation(reverseAnimation);
		} else if(mState == State.REFRESHING) {
			iv_arrow.setVisibility(View.GONE);
			pb_refresh.setVisibility(View.VISIBLE);
			tv_title.setVisibility(View.VISIBLE);
			tv_time.setVisibility(View.VISIBLE);
			iv_arrow.clearAnimation();
			
			tv_title.setText(getResources().getString(R.string.refreshing));

			mHeader.setPadding(0, 0, 0, 0);

			if (mOnRefreshListener != null) {
				mOnRefreshListener.onRefresh();
			}
		}
	}
	
	/**
	 * 当刷新数据完成时,必须使用这种方法隐藏顶部视图,如果不是顶部视图将显示所有的时间.
	 */
	public void onRefreshComplete() {
		mState = State.ORIGNAL;
		changeState();
		
		tv_time.setText(getResources().getString(R.string.update_time) + mSimpleDateFormat.format(new Date(System.currentTimeMillis())));
	}
	
	@Override
	public void setAdapter(ListAdapter adapter) {
		tv_time.setText(getResources().getString(R.string.update_time) + mSimpleDateFormat.format(new Date(System.currentTimeMillis())));
		super.setAdapter(adapter);
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		this.mOnScrollListener = l;
	}

	/**
	 * 建立刷新数据的监听事件
	 * @param listen
	 */
	public void setOnRefreshListener(OnRefreshListener listen) {
		this.mOnRefreshListener = listen;
	}
	
	/**
	 * 所有ListView的状态
	 *
	 */
	public enum State {
		ORIGNAL, PULL_TO_REFRESH, REFRESHING, RELEASE_TO_REFRESH;
	}
	
	/**
	 * 当刷新数据时实现该接口
	 *
	 */
	public interface OnRefreshListener{
		void onRefresh();
//		void onRefresh(int mode);
	}
}
