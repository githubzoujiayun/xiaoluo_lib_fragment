package com.xiaoluo.home;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.xiaoluo.BaseFragmentActivity;
import com.xiaoluo.SplashActivity;
import com.xiaoluo.entities.ModuleEntity;
import com.xiaoluo.net.Request;
import com.xiaoluo.net.Request.RequestMethod;
import com.xiaoluo.net.callback.JsonCallback;
import com.xiaoluo.utilities.Constants;
import com.xiaoluo.utilities.Trace;
import com.xiaoluo.utilities.UrlHelper;

public class HomeActivity extends BaseFragmentActivity implements TabListener, OnPageChangeListener {
	private ViewPager mHemoPager;
	private ActionBar mActionBar;
	private ModuleAdapter mAdapter;
	private ArrayList<ModuleEntity> mModuleEntities = new ArrayList<ModuleEntity>();
	private String[] tabs = new String[]{"萝莉", "女王", "御姐"};

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_home);
	}

	@Override
	protected void initializeData() {

	}

	@Override
	protected void initializeViews() {
		mHemoPager = (ViewPager)findViewById(R.id.mHemoPager);
		
		mAdapter = new ModuleAdapter(getSupportFragmentManager());
		mHemoPager.setAdapter(mAdapter);
//		mHemoPager.setOffscreenPageLimit(3);
		mHemoPager.setOnPageChangeListener(this);
		
		mActionBar = getSupportActionBar();
		// tabs应该展示在ActionBar上面
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Request request = new Request(UrlHelper.getModuleUrl(), RequestMethod.GET);
		request.setCallback(new JsonCallback<ArrayList<ModuleEntity>>() {

			@Override
			public void onFailure(Exception result) {
				Trace.d("HomeActivity.java:onFailure():"+ result);
			}

			@Override
			public void onSuccess(ArrayList<ModuleEntity> result) {
				mModuleEntities.clear();
				mModuleEntities.addAll(result);
				
				mAdapter.notifyDataSetChanged();
				
				// Add 3 tabs, specifying the tab's text and TabListener
				for (int i = 0; i < mModuleEntities.size(); i++) {
					mActionBar.addTab(mActionBar.newTab().setText(mModuleEntities.get(i).getModuleName()).setTabListener(HomeActivity.this));
				}
			}

			@Override
			public ArrayList<ModuleEntity> onPreHandler(
					ArrayList<ModuleEntity> t) {
				return t;
			}
		}.setReturnType(new TypeToken<ArrayList<ModuleEntity>>(){}.getType()));
		request.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return true;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		Trace.d("HomeActivity:onNewIntent");
		if (intent.getBooleanExtra(Constants.RESTART_APP, false)) {
			protectApplication();
		} else if (intent.getBooleanExtra(Constants.RELOGIN_APP, false)) {
			// reLogin();
		} else {
			// type = intent.getStringExtra(Constants.KEY_ACTION_TYPE);
			// UserInfo mUserInfo = (UserInfo)
			// intent.getSerializableExtra(UserInfo.USER_INFO);
			// if(TextUtil.isValidate(type) && mDrawerFragment != null) {
			// mDrawerFragment.setCurrentFragment(type, mUserInfo);
			// }
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Trace.d("HomeActivity:onRestart");
		if (getIntent().getBooleanExtra(Constants.RESTART_APP, false)) {
			protectApplication();
		}
	}

	private void protectApplication() {
		finish();
		startActivity(new Intent(this, SplashActivity.class));
	}
	
	// Since this is an object collection, use a FragmentStatePagerAdapter,
	// and NOT a FragmentPagerAdapter.
	public class ModuleAdapter extends FragmentStatePagerAdapter {
	    public ModuleAdapter(FragmentManager fm) {
	        super(fm);
	    }

	    @Override
	    public Fragment getItem(int i) {
	        return ModuleFragment.newInstance(mModuleEntities.get(i));
	    }

	    @Override
	    public int getCount() {
	        return mModuleEntities.size();
	    }

	    @Override
	    public CharSequence getPageTitle(int position) {
	        return mModuleEntities.get(position).getModuleName();
	    }

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			Trace.d("destroyItem"+ position);
			super.destroyItem(container, position, object);
		}

		@Override
		public Object instantiateItem(ViewGroup arg0, int arg1) {
			Trace.d("instantiateItem"+ arg1);
			return super.instantiateItem(arg0, arg1);
		}

		@Override
		public void setPrimaryItem(ViewGroup container, int position,
				Object object) {
			Trace.d("setPrimaryItem"+ position);
			super.setPrimaryItem(container, position, object);
		}
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		 mHemoPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		mActionBar.setSelectedNavigationItem(position);
	}
}
